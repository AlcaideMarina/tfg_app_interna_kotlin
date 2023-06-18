package com.example.hueverianieto.ui.views.allorders.fragments.allorders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllClientOrdersUseCase
import com.example.hueverianieto.domain.usecases.GetAllDocumentsIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    val getAllDocumentsIdUseCase: GetAllDocumentsIdUseCase,
    val getAllClientOrdersUseCase: GetAllClientOrdersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllOrdersViewState())
    val viewState: StateFlow<AllOrdersViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _allOrderList = MutableLiveData<List<OrderData?>?>()
    val allOrderList: LiveData<List<OrderData?>?> get() = _allOrderList

    fun getOrders() {
        viewModelScope.launch {
            _viewState.value = AllOrdersViewState(isLoading = true)
            when (val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _viewState.value = AllOrdersViewState(isLoading = false, error = true)
                }
                listOf<String>() -> {
                    _viewState.value = AllOrdersViewState(isLoading = false, isEmpty = true)
                }
                else -> {
                    val clientIdList: List<String?> = resultQ1
                    val orderList: MutableList<OrderData?> = mutableListOf()
                    for (id in clientIdList) {
                        if (id != null) {
                            when (val resultQ2 = getAllClientOrdersUseCase(id)) {
                                null -> {}
                                else -> {
                                    for (order in resultQ2) {
                                        if (order != null) {
                                            orderList.add(order)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _viewState.value = AllOrdersViewState(isLoading = false)
                    _allOrderList.value = orderList.sortedBy { it?.orderDatetime }.reversed()
                }
            }
        }
    }

    fun navigateToOrderDetail(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_allOrdersFragment_to_orderDetailFragment, bundle)
            ?: Log.e(
                AllOrdersViewModel::class.java.simpleName,
                "Error en la navegación a detalle de pedido"
            )
    }

    fun navigateToNewOrder(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_allOrdersFragment_to_newOrderFragment, bundle)
            ?: Log.e(
                AllOrdersViewModel::class.java.simpleName,
                "Error en la navegación a nuevo pedido"
            )
    }

}
