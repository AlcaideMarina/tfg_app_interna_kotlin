package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.DBOrderFieldData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentgridview.CustomGridLayoutManager
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var alertDialog: HNModalDialog
    private lateinit var eggPrices: EggPricesData
    private val newOrderViewModel: NewOrderViewModel by viewModels()

    private val dropdownClientItemsMap = mutableMapOf<String, String>()
    private val dropdownClientItems = mutableListOf<String>()
    private lateinit var clientData: ClientData
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()
    private var clientDocumentId: String? = null

    private lateinit var approxDeliveryDatetimeSelected: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )
        val args: NewOrderFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        this.newOrderViewModel.getPrices()
        this.newOrderViewModel.getClients()
        hideTextInputLayouts()
        disableTextInputLayouts()
        setRecyclerView(EggPricesData())
        getPaymentMethodDropdownValues()
        setOrderDateText()
        setButtons()

        lifecycleScope.launchWhenStarted {
            newOrderViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newOrderViewModel.eggPrices.observe(this) {
            eggPrices = it
            setRecyclerView(it)
        }
        this.newOrderViewModel.clientList.observe(this) {
            setClientsDropdownValues(it)
        }
        this.newOrderViewModel.clientData.observe(this) {
            clientData = it
            setClientText()
        }
        this.newOrderViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode != null) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        },
                        null
                    )
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.companyAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val listSelected = dropdownClientItems[position]
            clientDocumentId = dropdownClientItemsMap[listSelected]!!
            this.newOrderViewModel.getClient(clientDocumentId!!)
        }
        this.binding.deleteButton.setOnClickListener {
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.modifyButton.setOnClickListener {
            it.hideSoftInput()
            val paymentMethodSelected: Int? =
                when (this.binding.paymentMethodAutoCompleteTextView.text.toString()) {
                    requireContext().getString(R.string.in_cash) -> R.string.in_cash
                    requireContext().getString(R.string.per_receipt) -> R.string.per_receipt
                    requireContext().getString(R.string.transfer) -> R.string.transfer
                    else -> null
                }
            val dbOrderFieldData = getDBOrderFieldData()

            if (clientDocumentId == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Seleccione un cliente",
                    "Es necesario que seleccione un cliente al que asociar este pedido. Por favor, revise los datos y vuelva a intentarlo.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else if (paymentMethodSelected == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Faltan datos",
                    "El método de pago seleccionado no es válido. Por favor, revise los datos e inténtelo de nuevo",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else if (dbOrderFieldData == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Faltan datos",
                    "Se debe seleccionar, al menos, un producto para realizar el pedido. Por favor, revise los datos e inténtelo de nuevo",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            } else {
                val orderFieldMap = OrderUtils.parseDBOrderFieldDataToMap(dbOrderFieldData)
                val totalPrice = OrderUtils.getTotalPrice(dbOrderFieldData)

                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Precio final",
                    "El precio total del pedido será de $totalPrice €. ¿Desea continuar?",
                    "Atrás",
                    "Continuar",
                    { alertDialog.cancel() },
                    {
                        alertDialog.cancel()
                        continueOrder(
                            orderFieldMap,
                            paymentMethodSelected,
                            totalPrice
                        )
                    }
                )
            }
        }
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as NewOrderViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun hideTextInputLayouts() {
        with(this.binding) {
            orderIdTextView.visibility = View.GONE
            totalPriceTextLayout.visibility = View.GONE
            paidCheckedTextView.visibility = View.GONE
            orderDateTextLayout.visibility = View.GONE
            deliveryPersonTextLayout.visibility = View.GONE
            deliveryNoteTextLayout.visibility = View.GONE
            lotTextLayout.visibility = View.GONE
            deliveryDniTextLayout.visibility = View.GONE
            statusTextLayout.visibility = View.GONE
        }
    }

    private fun disableTextInputLayouts() {
        with(this.binding) {
            cifTextInputLayout.isEnabled = false
            directionTextInputLayout.isEnabled = false
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutPhone2.isEnabled = false

            this.companyAutoCompleteTextView.setTextColor(requireContext().getColor(R.color.black_color))
            this.paymentMethodAutoCompleteTextView.setTextColor(requireContext().getColor(R.color.black_color))
            this.deliveryDateTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
        }
    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        with(this.binding) {
            this.xlDozenTextInputLayout.setText("0")
            this.xlDozenPriceTextInputLayout.text = (eggPricesData.xlDozen ?: "-").toString() + " €/ud"
            this.xlDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.xlBoxTextInputLayout.setText("0")
            this.xlBoxPriceTextInputLayout.text = (eggPricesData.xlBox ?: "-").toString() + " €/ud"
            this.xlBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.lDozenTextInputLayout.setText("0")
            this.lDozenPriceTextInputLayout.text = (eggPricesData.lDozen ?: "-").toString() + " €/ud"
            this.lDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.lBoxTextInputLayout.setText("0")
            this.lBoxPriceTextInputLayout.text = (eggPricesData.lBox ?: "-").toString() + " €/ud"
            this.lBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.mDozenTextInputLayout.setText("0")
            this.mDozenPriceTextInputLayout.text = (eggPricesData.mDozen ?: "-").toString() + " €/ud"
            this.mDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.mBoxTextInputLayout.setText("0")
            this.mBoxPriceTextInputLayout.text = (eggPricesData.mBox ?: "-").toString() + " €/ud"
            this.mBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))

            this.sDozenTextInputLayout.setText("0")
            this.sDozenPriceTextInputLayout.text = (eggPricesData.sDozen ?: "-").toString() + " €/ud"
            this.sDozenTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
            this.sBoxTextInputLayout.setText("0")
            this.sBoxPriceTextInputLayout.text = (eggPricesData.sBox ?: "-").toString() + " €/ud"
            this.sBoxTextInputLayout.setTextColor(requireContext().getColor(R.color.black_color))
        }
    }

    private fun setClientsDropdownValues(clientDataList: List<ClientData?>?) {
        if (clientDataList != null) {
            for (client in clientDataList) {
                if (client != null) {
                    dropdownClientItemsMap[client.id.toString() + " - " + client.company] =
                        client.documentId!!
                    dropdownClientItems.add(client.id.toString() + " - " + client.company)
                }
            }
            this.binding.companyAutoCompleteTextView.setAdapter(
                ArrayAdapter(
                    requireContext(), R.layout.component_dropdown_list_item, dropdownClientItems
                )
            )
        }
    }

    private fun setClientText() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        with(binding) {
            directionTextInputLayout.setText(clientData.direction)
            cifTextInputLayout.setText(clientData.cif)
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
        }
    }

    private fun getPaymentMethodDropdownValues() {
        val values = Constants.paymentMethod.entries.iterator()
        for (v in values) {
            dropdownPaymentMethodItems.add(requireContext().getString(v.key))
        }
        this.binding.paymentMethodAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownPaymentMethodItems
            )
        )
    }

    private fun setOrderDateText() {
        approxDeliveryDatetimeSelected = Timestamp(Utils.addToDate(Date(), 3))
        this.binding.deliveryDateTextInputLayout.setText(
            Utils.parseDateToString(approxDeliveryDatetimeSelected.toDate())
        )
        this.binding.deliveryDateTextInputLayout.setOnClickListener { onClickScheduledDate() }
    }

    private fun onClickScheduledDate() {
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DATE)
        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            var dayStr = d.toString()
            var monthStr = (m + 1).toString()
            var yearStr = y.toString()
            if (dayStr.length < 2) dayStr = "0$dayStr"
            if (monthStr.length < 2) monthStr = "0$monthStr"
            if (yearStr.length < 4) yearStr = "0$yearStr"
            this.binding.deliveryDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            approxDeliveryDatetimeSelected =
                Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = Utils.addToDate(Date(), 3).time
        datePickerDialog.show()
    }

    private fun setButtons() {
        this.binding.modifyButtonText.text = "Guardar"
        this.binding.deleteButtonText.text = "Cancelar"
    }

    private fun getDBOrderFieldData() : DBOrderFieldData? {
        val xlBox =
            if(this.binding.xlBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.xlBoxTextInputLayout.text.toString().toIntOrNull()
        val xlDozen =
            if(this.binding.xlDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.xlDozenTextInputLayout.text.toString().toIntOrNull()
        val lBox =
            if(this.binding.lBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.lBoxTextInputLayout.text.toString().toIntOrNull()
        val lDozen =
            if(this.binding.lDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.lDozenTextInputLayout.text.toString().toIntOrNull()
        val mBox =
            if(this.binding.mBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.mBoxTextInputLayout.text.toString().toIntOrNull()
        val mDozen =
            if(this.binding.mDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.mDozenTextInputLayout.text.toString().toIntOrNull()
        val sBox =
            if(this.binding.sBoxTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.sBoxTextInputLayout.text.toString().toIntOrNull()
        val sDozen =
            if(this.binding.sDozenTextInputLayout.text.toString().toIntOrNull() == 0) null
            else this.binding.sDozenTextInputLayout.text.toString().toIntOrNull()

        return if (xlBox == null && xlDozen == null && lBox == null && lDozen == null &&
                mBox == null && mDozen == null && sBox == null && sDozen == null) {
            null
        } else {
            DBOrderFieldData(
                eggPrices.xlBox,
                xlBox,
                eggPrices.xlDozen,
                xlDozen,
                eggPrices.lBox,
                lBox,
                eggPrices.lDozen,
                lDozen,
                eggPrices.mBox,
                mBox,
                eggPrices.mDozen,
                mDozen,
                eggPrices.sBox,
                sBox,
                eggPrices.sDozen,
                sDozen,
            )
        }
    }

    private fun continueOrder(
        orderFieldMap: Map<String, Map<String, Number?>>,
        paymentMethodSelected: Int,
        totalPrice: Double
    ) {
        val orderData = OrderData(
            approxDeliveryDatetime = approxDeliveryDatetimeSelected,
            clientId = clientData.id!!,
            company = clientData.company,
            createdBy = "user_${currentUserData.id}",
            deliveryDatetime = null,
            deliveryDni = null,
            deliveryNote = null,
            deliveryPerson = null,
            lot = null,
            notes = null,
            order = orderFieldMap,
            orderDatetime = Timestamp(Date()),
            orderId = null,
            paid = false,
            paymentMethod = Constants.paymentMethod[paymentMethodSelected]!!.toLong(),
            status = 1,
            totalPrice = totalPrice,
            documentId = null
        )
        this.newOrderViewModel.addNewOrder(clientDocumentId!!, orderData)
    }

    companion object {
        private val TAG = NewOrderFragment::class.java.simpleName
    }

}
