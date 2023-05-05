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
import com.example.hueverianieto.domain.usecases.ClientDetailUseCase
import com.example.hueverianieto.domain.usecases.DeleteClientUseCase
import com.example.hueverianieto.domain.usecases.GetClientDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientDetailViewModel @Inject constructor(
    val clientDetailUseCase: ClientDetailUseCase,
    val deleteClientUseCase: DeleteClientUseCase,
    val getClientDataUseCase: GetClientDataUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ClientDetailViewState())
    val viewState: StateFlow<ClientDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _clientData = MutableLiveData<ClientData>()
    val clientData: LiveData<ClientData> get() = _clientData

    fun navigateToModifyClient(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_clientDetailFragment_to_modifyClientFragment, bundle)
            ?: Log.e(
                ClientDetailViewModel::class.simpleName,
                "Error en la navegación a Modificar cliente"
            )
    }

    fun deleteClient(documentId: String) {
        _viewState.value = ClientDetailViewState(isLoading = true)
        viewModelScope.launch {
            when(val result = deleteClientUseCase(documentId)) {
                false -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error al eliminar el cliente. Revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = false, correct = true)
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
            when (val result = getClientDataUseCase(documentId)) {
                null -> {
                    _viewState.value = ClientDetailViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = ClientDetailViewState(isLoading = false)
                    _clientData.value = result!!
                }
            }
        }
    }

}
