package com.example.hueverianieto.ui.views.allorders.fragments.modifyorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentgridview.CustomGridLayoutManager
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils

class ModifyOrderFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var alertDialog : HNModalDialog
    private lateinit var currentUserData : InternalUserData
    private lateinit var clientData : ClientData
    private lateinit var orderData : OrderData

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

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

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        disableTextInputLayouts()
        setTexts()
        setRecyclerView()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
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

        this.binding.totalPriceTextLayout.visibility = View.GONE
    }

    private fun setTexts() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        val statusApproxDeliveryDatetimeList = listOf<Long>(0, 1, 2)
        val deliveryDatetimeField : String = if (statusApproxDeliveryDatetimeList.contains(orderData.status)) {
            requireContext().getString(
                Utils.getKey(
                    Constants.orderStatus, orderData.status.toInt())!!) + " - " +
                    Utils.parseTimestampToString(orderData.approxDeliveryDatetime)
        } else if (orderData.status == (4).toLong()) {
            Utils.parseTimestampToString(orderData.deliveryDatetime) ?: ""
        } else if (orderData.status == (5).toLong()) {
            requireContext().getString(
                Utils.getKey(
                Constants.orderStatus, orderData.status.toInt())!!) + " - " +
                    Utils.parseTimestampToString(orderData.deliveryDatetime)
        } else {
            Utils.parseTimestampToString(orderData.deliveryDatetime)
                ?: Utils.parseTimestampToString(orderData.approxDeliveryDatetime)!!
        }

        with(this.binding) {
            orderIdTextView.text = "ID pedido: " + orderData.orderId.toString()
            companyAutoCompleteTextView.setText(clientData.company)
            directionTextInputLayout.setText(clientData.direction)
            cifTextInputLayout.setText(clientData.cif)
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
            orderDateTextInputLayout.setText(
                Utils.parseTimestampToString(orderData.orderDatetime) ?: "")
            deliveryDateTextInputLayout.setText(deliveryDatetimeField)
            //TODO: deliveryPersonTextInputLayout.setInputText(orderData.deliveryPerson ?: "")
            deliveryNoteTextInputLayout.setText(orderData.deliveryNote?.toString() ?: "")
            deliveryDniTextInputLayout.setText(orderData.deliveryDni ?: "")
        }

    }

    private fun setRecyclerView() {

        val list = OrderUtils.getOrderDataGridModel(orderData, true)

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

}
