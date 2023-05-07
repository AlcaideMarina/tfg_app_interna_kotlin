package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
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
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
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

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var currentUserData : InternalUserData
    private val newOrderViewModel : NewOrderViewModel by viewModels()

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

    private val dropdownClientItemsMap = mutableMapOf<String, String>()
    private val dropdownClientItems = mutableListOf<String>()
    private lateinit var clientData: ClientData
    private var dropdownPaymentMethodItems: MutableList<String> = mutableListOf()

    private lateinit var approxDeliveryDatetimeSelected : Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )
        val args : NewOrderFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

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
            setRecyclerView(it)
        }
        this.newOrderViewModel.clientList.observe(this) {
            setClientsDropdownValues(it)
        }
        this.newOrderViewModel.clientData.observe(this) {
            clientData = it
            setClientText()
        }
    }

    override fun setListeners() {
        this.binding.companyAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val listSelected = dropdownClientItems[position]
            val clientDocumentId = dropdownClientItemsMap[listSelected]!!
            this.newOrderViewModel.getClient(clientDocumentId)
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
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
        }
    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        val list = OrderUtils.getNewOrderGridModel(eggPricesData)

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

    private fun setClientsDropdownValues(clientDataList: List<ClientData?>?) {
        if (clientDataList != null) {
            for (client in clientDataList) {
                if (client != null) {
                    dropdownClientItemsMap[client.id.toString() + " - " + client.company] = client.documentId!!
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
                requireContext(), R.layout.component_dropdown_list_item, dropdownPaymentMethodItems)
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
            var monthStr = m.toString()
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

    private fun setButtons() {
        this.binding.modifyButton.setText("Guardar")
        this.binding.deleteButton.setText("Cancelar")
    }

}
