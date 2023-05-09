package com.example.hueverianieto.ui.views.clients.fragments.clientorders

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
import com.example.hueverianieto.ui.views.allorders.fragments.allorders.AllOrdersViewState
import com.example.hueverianieto.ui.views.clients.fragments.clientdetail.ClientDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientOrdersViewModel @Inject constructor(
    val getAllClientOrdersUseCase: GetAllClientOrdersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ClientOrdersViewState())
    val viewState: StateFlow<ClientOrdersViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _allOrderList = MutableLiveData<List<OrderData?>?>()
    val allOrderList: LiveData<List<OrderData?>?> get() = _allOrderList

    fun getOrders(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ClientOrdersViewState(isLoading = true)
            when(val result = getAllClientOrdersUseCase(documentId)) {
                null -> {
                    _viewState.value = ClientOrdersViewState(isLoading = false, error = true)
                }
                else -> {
                    val orderList : MutableList<OrderData?> = mutableListOf()
                    for (order in result) {
                        if (order != null) {
                            orderList.add(order)
                        }
                    }
                    _allOrderList.value = orderList
                    _viewState.value = ClientOrdersViewState(isLoading = false)
                }
            }
        }
    }

    fun navigateToOrderDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_clientOrdersFragment_to_orderDetailFragment2, bundle)
            ?: Log.e(
                AllOrdersViewState::class.java.simpleName,
                "Error en la navegación a detalle de pedido"
            )
    }

}