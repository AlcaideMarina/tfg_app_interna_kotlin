package com.example.hueverianieto.ui.views.clients.fragments.newclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.usecases.CreateAuthUserUseCase
import com.example.hueverianieto.domain.usecases.GetClientIdUseCase
import com.example.hueverianieto.domain.usecases.NewClientUseCase
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewClientViewModel @Inject constructor(
    val newClientUseCase: NewClientUseCase,
    val getClientIdUseCase: GetClientIdUseCase,
    val createAuthUserUseCase: CreateAuthUserUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewClientViewState())
    val viewState: StateFlow<NewClientViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addNewClient(clientData: ClientData) {
        viewModelScope.launch {
            _viewState.value = NewClientViewState(isLoading = true)
            if (Utils.isValidEmail(clientData.email)) {
                if (clientData.hasAccount && clientData.user != null) {
                    when (val newUid = createAuthUserUseCase(clientData.email, clientData.user!!)) {
                        null -> {
                            _viewState.value = NewClientViewState(isLoading = false, error = true)
                            _alertDialog.value = AlertOkData(
                                "Error",
                                "Se ha producido un error al crear el usuario de la aplicación. Revise los datos e inténtelo de nuevo. Recuerde que no se puede tener dos cuentas con el mismo correo y que el usuario debe tener, al menos, 6 caracteres.",
                                true
                            )
                        }
                        else -> {
                            clientData.uid = newUid
                            when (val clientId = getClientIdUseCase()) {
                                null -> {
                                    _viewState.value =
                                        NewClientViewState(isLoading = false, error = true)
                                }
                                else -> {
                                    clientData.id = clientId
                                    when (newClientUseCase(clientData)) {
                                        false -> {
                                            _viewState.value = NewClientViewState(
                                                isLoading = false,
                                                error = true
                                            )
                                            _alertDialog.value = AlertOkData(
                                                title = "Error",
                                                text = "Se ha producido un error al guardar el nuevo cliente. Por favor, revise los datos e inténtelo de nuevo.",
                                                true
                                            )
                                        }
                                        true -> {
                                            _viewState.value = NewClientViewState(
                                                isLoading = false,
                                                error = false
                                            )
                                            _alertDialog.value = AlertOkData(
                                                title = "Cliente guardado",
                                                text = "El cliente ha sido guardado correctamente en la base de datos.",
                                                true,
                                                customCode = 0
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    when (val clientId = getClientIdUseCase()) {
                        null -> {
                            _viewState.value = NewClientViewState(isLoading = false, error = true)
                        }
                        else -> {
                            clientData.id = clientId
                            when (newClientUseCase(clientData)) {
                                false -> {
                                    _viewState.value = NewClientViewState(
                                        isLoading = false,
                                        error = true
                                    )
                                    _alertDialog.value = AlertOkData(
                                        title = "Error",
                                        text = "Se ha producido un error al guardar el nuevo cliente. Por favor, revise los datos e inténtelo de nuevo.",
                                        true
                                    )
                                }
                                true -> {
                                    _viewState.value = NewClientViewState(
                                        isLoading = false,
                                        error = false
                                    )
                                    _alertDialog.value = AlertOkData(
                                        title = "Cliente guardado",
                                        text = "El cliente ha sido guardado correctamente en la base de datos.",
                                        true,
                                        customCode = 0
                                    )
                                }
                            }
                        }
                    }
                }

            } else {
                _viewState.value = NewClientViewState(isLoading = false, error = true)
                _alertDialog.value = AlertOkData(
                    title = "Correo no válido",
                    text = "El correo introducido no tiene un formato válido. Por favor, revise los datos e inténtelo de nuevo.",
                    true
                )
            }

        }
    }

}
