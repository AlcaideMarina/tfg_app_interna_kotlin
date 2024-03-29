package com.example.hueverianieto.ui.views.clients.fragments.clientorders

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllOrdersBinding
import com.example.hueverianieto.ui.components.componentordercontainer.HNOrderContainerAdapter
import com.example.hueverianieto.ui.views.allorders.fragments.allorders.AllOrdersViewState
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientOrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllOrdersBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var clientData: ClientData
    private val clientOrdersViewModel: ClientOrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.binding = FragmentAllOrdersBinding.inflate(
            inflater, container, false
        )

        val args: ClientOrdersFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.clientData = args.clientData
        (activity as BaseActivity).changeTopBarName("Pedidos del cliente - ID: ${clientData.id}")

        return this.binding.root
    }

    override fun configureUI() {
        this.binding.addButton.visibility = View.GONE
        this.clientOrdersViewModel.getOrders(clientData.documentId!!)
        lifecycleScope.launchWhenStarted {
            clientOrdersViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        clientOrdersViewModel.allOrderList.observe(this) { orderDataList ->
            if (orderDataList == null) {
                this.binding.orderRecyclerView.visibility = View.GONE
            } else {
                val orderList = mutableListOf<OrderContainerModel>()
                for (orderData in orderDataList) {
                    if (orderData != null &&
                        orderData.status != Constants.orderStatus[R.string.cancelled]!!.toLong()
                    ) {
                        val orderContainerModel = OrderContainerModel(
                            orderData.orderDatetime,
                            orderData.orderId!!,
                            orderData.clientId.toString() + " - " + orderData.company,
                            OrderUtils.getOrderSummary(OrderUtils.orderDataToBDOrderModel(orderData)),
                            orderData.totalPrice ?: -1,
                            orderData.status,
                            orderData.deliveryDni
                        ) {
                            this.clientOrdersViewModel.navigateToOrderDetail(
                                this.view,
                                bundleOf(
                                    "orderData" to orderData,
                                    "currentUserData" to currentUserData
                                )
                            )
                        }
                        orderList.add(orderContainerModel)
                    }
                }
                if (orderList.isEmpty()) {
                    this.binding.orderRecyclerView.visibility = View.GONE
                } else {
                    this.binding.orderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.orderRecyclerView.adapter = HNOrderContainerAdapter(orderList)
                    this.binding.orderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as AllOrdersViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    companion object {
        private val TAG = ClientOrdersFragment::class.java.simpleName
    }
}
