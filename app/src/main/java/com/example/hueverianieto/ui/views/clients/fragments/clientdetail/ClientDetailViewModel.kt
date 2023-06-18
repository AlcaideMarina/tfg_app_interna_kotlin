package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

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
import com.example.hueverianieto.domain.usecases.DeleteDocumentFieldUseCase
import com.example.hueverianieto.domain.usecases.GetAllClientOrdersUseCase
import com.example.hueverianieto.domain.usecases.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientDetailViewModel @Inject constructor(
    val deleteDocumentFieldUseCase: DeleteDocumentFieldUseCase,
    val getUserDataUseCase: GetUserDataUseCase,
    val getAllClientOrdersUseCase: GetAllClientOrdersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ClientDetailViewState())
    val viewState: StateFlow<ClientDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _clientData = MutableLiveData<ClientData>()
    val clientData: LiveData<ClientData> get() = _clientData

    private var _allOrderList = MutableLiveData<List<OrderData?>?>()
    val allOrderList: LiveData<List<OrderData?>?> get() = _allOrderList

    fun navigateToModifyClient(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_clientDetailFragment_to_modifyClientFragment, bundle)
            ?: Log.e(
                ClientDetailViewModel::class.simpleName,
                "Error en la navegación a Modificar cliente"
            )
    }

    fun deleteClient(documentId: String) {
        _viewState.value = ClientDetailViewState(isLoading = true)
        viewModelScope.launch {
            when (val result = deleteDocumentFieldUseCase(documentId, "client_info")) {
                false -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error al eliminar el cliente. Revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value =
                        ClientDetailViewState(isLoading = false, error = false, correct = true)
                    _alertDialog.value = AlertOkData(
                        "Cliente eliminado",
                        "El cliente ha sido eliminado correctamente",
                        true,
                        0
                    )
                }
            }
        }
    }

    fun getClientData(documentId: String) {
        _viewState.value = ClientDetailViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = ClientDetailViewState(isLoading = true)
            when (val result = getUserDataUseCase(documentId, "client_info")) {
                null -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = ClientDetailViewState(isLoading = false)
                    _clientData.value = result!! as ClientData
                }
            }
        }
    }

    fun getOrders(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ClientDetailViewState(isLoading = true)
            when (val result = getAllClientOrdersUseCase(documentId)) {
                null -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = true)
                }
                else -> {
                    val orderList: MutableList<OrderData?> = mutableListOf()
                    for (order in result) {
                        if (order != null) {
                            orderList.add(order)
                        }
                    }
                    _allOrderList.value = orderList
                    _viewState.value = ClientDetailViewState(isLoading = false)
                }
            }
        }
    }

    fun navigateToAllClientOrders(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_clientDetailFragment_to_clientOrdersFragment, bundle)
            ?: Log.e(
                ClientDetailViewModel::class.simpleName,
                "Error en la navegación a todos los pedidos del cliente"
            )
    }

    fun navigateToOrderDetail(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_clientDetailFragment_to_orderDetailFragment2, bundle)
            ?: Log.e(
                ClientDetailViewModel::class.java.simpleName,
                "Error en la navegación a detalle de pedido"
            )
    }

}
