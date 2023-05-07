package com.example.hueverianieto.ui.views.allorders.fragments.orderdetail

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
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.DeleteOrderUseCase
import com.example.hueverianieto.domain.usecases.GetClientWithIdUseCase
import com.example.hueverianieto.domain.usecases.GetOrderUseCase
import com.example.hueverianieto.ui.views.clients.fragments.clientdetail.ClientDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    val getClientWithIdUseCase: GetClientWithIdUseCase,
    val deleteOrderUseCase: DeleteOrderUseCase,
    val getOrderUseCase: GetOrderUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(OrderDetailViewState())
    val viewState: StateFlow<OrderDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _clientData = MutableLiveData<ClientData?>()
    val clientData: LiveData<ClientData?> get() = _clientData

    private var _orderData = MutableLiveData<OrderData?>()
    val orderData: LiveData<OrderData?> get() = _orderData

    fun getClientData(clientId: Long) {
        _viewState.value = OrderDetailViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = OrderDetailViewState(isLoading = true)
            when (val result = getClientWithIdUseCase(clientId)) {
                null -> {
                    _viewState.value = OrderDetailViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = OrderDetailViewState(isLoading = false)
                    _clientData.value = result!!
                }
            }
        }
    }

    fun deleteOrder(clientDocumentId: String, orderDocumentId: String) {
        _viewState.value = OrderDetailViewState(isLoading = true)
        viewModelScope.launch {
            when(val result = deleteOrderUseCase(clientDocumentId, orderDocumentId)) {
                false -> {
                    _viewState.value = OrderDetailViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error al eliminar el pedido. Revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = OrderDetailViewState(isLoading = false, error = false, correct = true)
                    _alertDialog.value = AlertOkData(
                        "Pedido eliminado",
                        "El pedido ha sido eliminado correctamente",
                        true,
                        0
                    )
                }
            }
        }
    }

    fun getOrder(clientDocumentId: String, orderDocumentId: String) {
        viewModelScope.launch {
            _viewState.value = OrderDetailViewState(isLoading = true)
            when(val result = getOrderUseCase(clientDocumentId, orderDocumentId)) {
                null -> {
                    _viewState.value = OrderDetailViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error al intentar acceder a los datos del pedido. Por favor, inténtelo de nuevo.",
                        true
                    )
                }
                else -> {
                    _viewState.value = OrderDetailViewState(isLoading = false)
                    _orderData.value = result!!
                }
            }
        }
    }

    fun navigateToModifyOrder(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_orderDetailFragment_to_modifyOrderFragment, bundle)
            ?: Log.e(
                OrderDetailViewModel::class.java.simpleName,
                "Error en la navigación a Modificar pedido"
            )
    }

}
