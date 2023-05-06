package com.example.hueverianieto.ui.views.allorders.fragments.allorders

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
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllOrdersBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentordercontainer.HNOrderContainerAdapter
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import com.example.hueverianieto.ui.views.main.MainActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class AllOrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllOrdersBinding
    private lateinit var currentUserData: InternalUserData
    private var fromNewOrderFragment by Delegates.notNull<Boolean>()
    private lateinit var alertDialog: HNModalDialog
    private var isFirst = true
    private val allOrdersViewModel : AllOrdersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as AllOrdersActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        this.currentUserData = (activity as AllOrdersActivity).currentUserData
        if (isFirst) {
            this.fromNewOrderFragment = (activity as AllOrdersActivity).fromNewOrderFragment
            isFirst = false
        } else {
            this.fromNewOrderFragment = false
        }

        this.binding = FragmentAllOrdersBinding.inflate(
            inflater, container, false
        )

        return this.binding.root
    }

    override fun configureUI() {
        this.alertDialog = HNModalDialog(requireContext())
        this.allOrdersViewModel.getOrders()
        lifecycleScope.launchWhenStarted {
            allOrdersViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
        if (this.fromNewOrderFragment) {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Pedido realizado",
                "Su pedido se ha realizado correctamente.",
                "De acuerdo",
                null,
                {
                    alertDialog.cancel()
                    this.fromNewOrderFragment = false
                },
                null
            )
        }
    }

    override fun setObservers() {
        allOrdersViewModel.allOrderList.observe(this) { orderDataList ->
            if (orderDataList == null) {
                // Error
            } else {
                val orderList = mutableListOf<OrderContainerModel>()
                for(orderData in orderDataList) {
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
                            this.allOrdersViewModel.navigateToOrderDetail(
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
                    // TODO: Vacío
                } else {
                    this.binding.orderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.orderRecyclerView.adapter = HNOrderContainerAdapter(orderList)
                    this.binding.orderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        // It is not necessary
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as AllOrdersViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    if (state.error) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    } else if (state.isEmpty) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }
    }


    companion object {
        private val TAG = AllOrdersFragment::class.java.simpleName
    }
}