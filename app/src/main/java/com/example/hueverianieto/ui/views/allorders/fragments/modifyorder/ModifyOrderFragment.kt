package com.example.hueverianieto.ui.views.allorders.fragments.modifyorder

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
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentgridview.CustomGridLayoutManager
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import com.example.hueverianieto.ui.views.allorders.fragments.allorders.AllOrdersFragment
import com.example.hueverianieto.ui.views.allorders.fragments.allorders.AllOrdersViewState
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class ModifyOrderFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var alertDialog : HNModalDialog
    private lateinit var currentUserData : InternalUserData
    private lateinit var clientData : ClientData
    private lateinit var orderData : OrderData
    private val modifyOrderViewModel : ModifyOrderViewModel by viewModels()

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

    private lateinit var approxDeliveryDatetimeSelected : Timestamp
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()
    private var dropdownStatusItems: MutableList<String> = mutableListOf()
    private var dropdownDeliveryPersonItemMap = mutableMapOf<String, String>()
    private var dropdownDeliveryPersonItem = mutableListOf<String>()
    private var deliveryPersonDocumentId : String? = null

    private var finished: Boolean = false
    private var paid: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllOrdersActivity).configNav(true)
        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )

        val args : ModifyOrderFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.clientData = args.clientData
        this.orderData = args.orderData
        this.paid = orderData.paid

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        this.modifyOrderViewModel.getAllDeliveryPerson()
        if (orderData.deliveryPerson != null)
            this.modifyOrderViewModel.getDeliveryPerson(orderData.deliveryPerson!!)
        this.modifyOrderViewModel.getPrices()
        setButtons()
        disableTextInputLayouts()
        setTexts()
        getPaymentMethodDropdownValues()
        getStatusDropdownValues()

        lifecycleScope.launchWhenStarted {
            modifyOrderViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.modifyOrderViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 1) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            (activity as BaseActivity).goBackFragments()
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
        this.modifyOrderViewModel.deliveryPersonList.observe(this) {
            setDeliveryPersonDropdownValues(it)
        }
        this.modifyOrderViewModel.deliveryPerson.observe(this) { deliveryPerson ->
            if (deliveryPerson != null) {
                val text =
                    "${deliveryPerson.id} - ${deliveryPerson.name} ${deliveryPerson.surname}"
                this.binding.deliveryPersonAutoCompleteTextView.setText(text, false)
            }
        }
        this.modifyOrderViewModel.eggPrices.observe(this) {
            setRecyclerView(it)
        }
    }

    override fun setListeners() {
        this.binding.paidCheckedTextView.setOnClickListener {
            this.binding.paidCheckedTextView.isChecked = !this.binding.paidCheckedTextView.isChecked
        }
        this.binding.deleteButton.setOnClickListener {
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.deliveryPersonAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val listSelected = dropdownDeliveryPersonItem[position]
            deliveryPersonDocumentId = dropdownDeliveryPersonItemMap[listSelected]!!
        }
        this.binding.modifyButton.setOnClickListener {
            it.hideSoftInput()
            val paymentMethodSelected : Int? = when (this.binding.paymentMethodAutoCompleteTextView.text.toString()) {
                requireContext().getString(R.string.in_cash) -> R.string.in_cash
                requireContext().getString(R.string.per_receipt) -> R.string.per_receipt
                requireContext().getString(R.string.transfer) -> R.string.transfer
                else -> null
            }
            val statusSelected : Int? = when (this.binding.statusAutoCompleteTextView.text.toString()) {
                requireContext().getString(R.string.price_pending) -> R.string.price_pending
                requireContext().getString(R.string.backordered) -> R.string.backordered
                requireContext().getString(R.string.in_delivery) -> R.string.in_delivery
                requireContext().getString(R.string.delivered) -> R.string.delivered
                requireContext().getString(R.string.delivery_attempt) -> R.string.delivery_attempt
                requireContext().getString(R.string.cancelled) -> R.string.cancelled
                else -> null
            }
            val dbOrderFieldData = OrderUtils.getOrderStructure(this.binding.orderRecyclerView)

            if (paymentMethodSelected == null) {
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
            } else if (statusSelected == null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Faltan datos",
                    "El estado seleccionado no es válido. Por favor, revise los datos e inténtelo de nuevo",
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
                var a = this.binding.deliveryPersonAutoCompleteTextView
                val orderFieldMap = OrderUtils.parseDBOrderFieldDataToMap(dbOrderFieldData)
                val totalPrice = OrderUtils.getTotalPrice(dbOrderFieldData)
                val deliveryDniAux =
                    if (this.binding.deliveryDniTextInputLayout.text.toString() == "") null
                    else this.binding.deliveryDniTextInputLayout.text.toString()
                val deliveryNoteAux =
                    if (this.binding.deliveryNoteTextInputLayout.text.toString() == "") null
                    else this.binding.deliveryNoteTextInputLayout.text.toString().toLong()
                val deliveryLotAux =
                    if (this.binding.lotTextInputLayout.text.toString() == "") null
                    else this.binding.lotTextInputLayout.text.toString()
                val deliveryPersonAux =
                    if (this.binding.deliveryPersonAutoCompleteTextView.text.toString() == "") orderData.deliveryPerson
                    else deliveryPersonDocumentId

                val statusInt = Constants.orderStatus[statusSelected]!!
                val deliveryDatetime = if (listOf(3, 4, 5).contains(statusInt)) {
                    Timestamp.now()
                } else {
                    orderData.deliveryDatetime
                }
                if (statusInt == 3 && deliveryDniAux == null) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        "Faltan el DNI de entrega",
                        "No se puede marcar un pedido como entregado si falta el DNI que confirme la entrega",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                } else {
                    val orderData = OrderData(
                        approxDeliveryDatetime = approxDeliveryDatetimeSelected,
                        clientId = clientData.id!!,
                        company = clientData.company,
                        createdBy = orderData.createdBy,
                        deliveryDatetime = deliveryDatetime,
                        deliveryDni = deliveryDniAux,
                        deliveryNote = deliveryNoteAux,
                        deliveryPerson = deliveryPersonAux,
                        lot = deliveryLotAux,
                        notes = null,
                        order = orderFieldMap,
                        orderDatetime = orderData.orderDatetime,
                        orderId = orderData.orderId,
                        paid = this.binding.paidCheckedTextView.isChecked,
                        paymentMethod = Constants.paymentMethod[paymentMethodSelected]!!.toLong(),
                        status = statusInt.toLong(),
                        totalPrice = totalPrice,
                        documentId = this.orderData.documentId
                    )
                    modifyOrderViewModel.updateOrder(clientData.documentId!!, orderData)
                }


            }
        }
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as ModifyOrderViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun setButtons() {
        this.binding.modifyButton.setText("Guardar")
        this.binding.deleteButton.setText("Cancelar")
    }

    private fun disableTextInputLayouts() {
        this.binding.companyTextInputLayout.isEnabled = false
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.cifTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
        this.binding.orderDateTextInputLayout.isEnabled = false
        if (listOf<Long>(2, 3, 4, 5).contains(this.orderData.status)) {
            this.binding.deliveryDateTextInputLayout.isEnabled = false
            if (this.orderData.status == (3).toLong()) {
                finished = true
                this.binding.statusTextInputLayout.isEnabled = !finished
                this.binding.deliveryDniTextInputLayout.isEnabled = !finished
            }
        }
        if (paid) {
            this.binding.paymentMethodTextInputLayout.isEnabled = false
        }

        this.binding.totalPriceTextLayout.visibility = View.GONE
    }

    private fun setTexts() {

        this.binding.deliveryDateTextInputLayout.setOnClickListener { onClickScheduledDate() }
        approxDeliveryDatetimeSelected = orderData.approxDeliveryDatetime

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        with(this.binding) {
            orderIdTextView.text = "ID pedido: " + orderData.orderId.toString()
            companyAutoCompleteTextView.setText(clientData.company, false)
            directionTextInputLayout.setText(clientData.direction)
            cifTextInputLayout.setText(clientData.cif)
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
            orderDateTextInputLayout.setText(
                Utils.parseTimestampToString(orderData.orderDatetime) ?: "")
            deliveryDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                    orderData.deliveryDatetime ?: orderData.approxDeliveryDatetime))
            //TODO: deliveryPersonTextInputLayout.setInputText(orderData.deliveryPerson ?: "")
            deliveryNoteTextInputLayout.setText(orderData.deliveryNote?.toString() ?: "")
            deliveryDniTextInputLayout.setText(orderData.deliveryDni ?: "")
            lotTextInputLayout.setText(orderData.lot ?: "")
            paidCheckedTextView.isChecked = orderData.paid
            paymentMethodAutoCompleteTextView.setText(
                requireContext().getString(
                    Utils.getKey(Constants.paymentMethod, orderData.paymentMethod.toInt())!!),false)
            statusAutoCompleteTextView.setText(
                requireContext().getString(
                    Utils.getKey(Constants.orderStatus, orderData.status.toInt())!!), false)
        }

    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        val isEnabled = if (paid) {
            false
        } else !finished

        val list = OrderUtils.getOrderDataModifyGridModel(orderData, eggPricesData, isEnabled)

        val manager = CustomGridLayoutManager(this.context, 4)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerViewTitles.contains(position)) 4
                else if(recyclerViewTextInputLayouts.contains(position)) 2
                else 1
            }
        }

        this.binding.orderRecyclerView.layoutManager = manager
        this.binding.orderRecyclerView.adapter = HNGridTextAdapter(list)

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
            approxDeliveryDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = Utils.addToDate(Date(), 3).time
        datePickerDialog.show()
    }

    private fun getPaymentMethodDropdownValues() {
        val values = Constants.paymentMethod.entries.iterator()
        for (v in values) {
            dropdownPaymentMethodItems.add(requireContext().getString(v.key))
        }
        this.binding.paymentMethodAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownPaymentMethodItems)
        )
    }

    private fun getStatusDropdownValues() {
        val values = Constants.orderStatus.entries.iterator()
        for (v in values) {
            dropdownStatusItems.add(requireContext().getString(v.key))
        }
        this.binding.statusAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownStatusItems)
        )
    }

    private fun setDeliveryPersonDropdownValues(internalUserDataList: List<InternalUserData?>) {
        for (deliveryPerson in internalUserDataList) {
            if (deliveryPerson != null) {
                dropdownDeliveryPersonItemMap[deliveryPerson.id.toString() + " - " + deliveryPerson.name + " " + deliveryPerson.surname] = deliveryPerson.documentId!!
                dropdownDeliveryPersonItem.add(deliveryPerson.id.toString() + " - " + deliveryPerson.name + " " + deliveryPerson.surname)
            }
        }
        this.binding.deliveryPersonAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                requireContext(), R.layout.component_dropdown_list_item, dropdownDeliveryPersonItem
            )
        )
    }

    companion object {
        private val TAG = ModifyOrderFragment::class.java.simpleName
    }

}
