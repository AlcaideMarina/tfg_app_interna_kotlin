package com.example.hueverianieto.ui.views.main.fragments.orderanddelivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentOrdersAndDeliveryBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentordercontainer.HNOrderContainerAdapter
import com.example.hueverianieto.ui.views.main.MainActivity
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderAndDeliveryFragment : BaseFragment() {

    private lateinit var binding : FragmentOrdersAndDeliveryBinding
    private lateinit var internalUserData: InternalUserData
    private lateinit var alertDialog: HNModalDialog
    private val orderAndDeliveryViewModel : OrderAndDeliveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        internalUserData = (activity as MainActivity).internalUserData
        binding =
            FragmentOrdersAndDeliveryBinding.bind(inflater.inflate(R.layout.fragment_orders_and_delivery, container, false))
        return binding.root
    }

    override fun configureUI() {
        setButtons()
        this.binding.dateTextView.setText(
            Utils.parseTimestampToString(Timestamp.now())
        )
        this.orderAndDeliveryViewModel.getTodayOrders()
    }

    override fun setObservers() {
        this.orderAndDeliveryViewModel.allOrderList.observe(this) { orderDataList ->
            if (orderDataList == null) {
                // TODO: error
            } else {

                val orderList = mutableListOf<OrderContainerModel>()
                for (orderData in orderDataList) {
                    if (orderData != null) {
                        val orderContainerModel = OrderContainerModel(
                            orderData.orderDatetime,
                            orderData.orderId!!,
                            orderData.clientId.toString() + " - " + orderData.company,
                            OrderUtils.getOrderSummary(OrderUtils.orderDataToBDOrderModel(orderData)),
                            orderData.totalPrice ?: -1,
                            orderData.status,
                            orderData.deliveryDni
                        ) {
                            // TODO
                        }
                        orderList.add(orderContainerModel)
                    }
                }
                if (orderList.isEmpty()) {
                    // TODO: Vacío
                } else {
                    this.binding.todayOrderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.todayOrderRecyclerView.adapter = HNOrderContainerAdapter(orderList)
                    this.binding.todayOrderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setButtons() {
        this.binding.newOrderButton.setText("Nuevo pedido")
        this.binding.seeAllOrdersButton.setText("Ver todo")
    }


}