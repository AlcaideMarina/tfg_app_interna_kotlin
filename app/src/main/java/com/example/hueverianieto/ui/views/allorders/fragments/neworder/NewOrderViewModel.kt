package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.*
import com.example.hueverianieto.ui.views.clients.fragments.clientdetail.ClientDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    val getPricesUseCase : GetPricesUseCase,
    val getAllClientsUseCase: GetAllClientsUseCase,
    val getUserDataUseCase: GetUserDataUseCase,
    val getNewOrderIdUseCase: GetNewOrderIdUseCase,
    val newOrderUseCase: NewOrderUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewOrderViewState())
    val viewState: StateFlow<NewOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _eggPrices = MutableLiveData(EggPricesData())
    val eggPrices: LiveData<EggPricesData> get() = _eggPrices

    private val _clientList = MutableLiveData<List<ClientData?>?>()
    val clientList: LiveData<List<ClientData?>?> get() = _clientList

    private var _clientData = MutableLiveData<ClientData>()
    val clientData: LiveData<ClientData> get() = _clientData

    fun getPrices() {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when(val result = getPricesUseCase()) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                    _eggPrices.value = EggPricesData()
                }
                else -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _eggPrices.value = result
                }
            }
        }
    }

    fun getClients() {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when(val result = getAllClientsUseCase(false)) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                    _clientList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _clientList.value = listOf()
                }
                else -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _clientList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

    fun getClient(documentId: String) {
        _viewState.value = NewOrderViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when (val result = getUserDataUseCase(documentId, "client_info")) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _clientData.value = result!! as ClientData
                }
            }
        }
    }

    fun addNewOrder(clientDocumentId : String, orderData: OrderData) {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when(val orderId = getNewOrderIdUseCase(clientDocumentId)) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error. Por favor, revise los datos y vuelva a intentarlo",
                        true
                    )
                }
                else -> {
                    orderData.orderId = orderId
                    when(newOrderUseCase(clientDocumentId, orderData)) {
                        false -> {
                            _viewState.value = NewOrderViewState(isLoading = false, error = true)
                            _alertDialog.value = AlertOkData(
                                title = "Error",
                                text = "Se ha producido un error. Por favor, revise los datos y vuelva a intentarlo",
                                true
                            )
                        }
                        true -> {
                            _viewState.value = NewOrderViewState(isLoading = false)
                            _alertDialog.value = AlertOkData(
                                "Pedido realizado",
                                "Su pedido se ha realizado correctamente.",
                                true,
                                0
                            )
                        }
                    }
                }
            }
        }
    }


}