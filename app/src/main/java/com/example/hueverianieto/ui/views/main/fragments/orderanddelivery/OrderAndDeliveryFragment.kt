package com.example.hueverianieto.ui.views.main.fragments.orderanddelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentOrdersAndDeliveryBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentordercontainer.HNOrderContainerAdapter
import com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers.AllInternalUsersFragment
import com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers.AllInternalUsersViewState
import com.example.hueverianieto.ui.views.main.MainActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderAndDeliveryFragment : BaseFragment() {

    private lateinit var binding : FragmentOrdersAndDeliveryBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var alertDialog: HNModalDialog
    private val orderAndDeliveryViewModel : OrderAndDeliveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        currentUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentOrdersAndDeliveryBinding.bind(inflater.inflate(R.layout.fragment_orders_and_delivery, container, false))
        return binding.root
    }

    override fun configureUI() {
        setButtons()
        this.binding.dateTextView.text = Utils.parseTimestampToString(Timestamp.now())
        this.orderAndDeliveryViewModel.getTodayOrders()
        lifecycleScope.launchWhenStarted {
            orderAndDeliveryViewModel.getTodayOrders()
            orderAndDeliveryViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.orderAndDeliveryViewModel.allOrderList.observe(this) { orderDataList ->
            if (orderDataList == null) {
                // TODO: error
            } else {

                val orderList = mutableListOf<OrderContainerModel>()
                for (orderData in orderDataList) {
                    if (orderData != null &&
                        orderData.status != Constants.orderStatus[R.string.cancelled]!!.toLong()) {
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
                    this.binding.todayOrderRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoInternalUsers.visibility = View.VISIBLE
                } else {
                    this.binding.todayOrderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.todayOrderRecyclerView.adapter = HNOrderContainerAdapter(orderList)
                    this.binding.todayOrderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.seeAllOrdersButton.setOnClickListener {
            this.orderAndDeliveryViewModel.navigateToAllOrdersActivity(
                requireContext(), currentUserData
            )
        }
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as OrderAndDeliveryViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    if (state.error) {
                        // TODO: Popup error
                    } else if (state.isEmpty) {
                        // TODO: Popup está vacío
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString(), e)
        }
    }

    override fun onResume() {
        this.orderAndDeliveryViewModel.getTodayOrders()
        this.binding.todayOrderRecyclerView.visibility = View.GONE
        super.onResume()
    }

    private fun setButtons() {
        this.binding.seeAllOrdersButton.setText("Ver todo")
    }

    companion object {
        private val TAG = OrderAndDeliveryFragment::class.java.simpleName
    }


}