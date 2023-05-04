package com.example.hueverianieto.ui.views.clients.fragments.modifyclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertErrorOkData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.usecases.CreateAuthUserUseCase
import com.example.hueverianieto.domain.usecases.UpdateFirestoreUserUseCase
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyClientViewModel @Inject constructor(
    private val createAuthUserUseCase: CreateAuthUserUseCase,
    private val updateFirestoreUserUseCase: UpdateFirestoreUserUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyClientViewState())
    val viewState: StateFlow<ModifyClientViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertErrorOkData())
    val alertDialog: LiveData<AlertErrorOkData> get() = _alertDialog

    fun updateUser(clientData: ClientData) {

        _viewState.value = ModifyClientViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = ModifyClientViewState(isLoading = true)
            if(Utils.isValidEmail(clientData.email)) {
                if (clientData.hasAccount) {
                    when (val result = createAuthUserUseCase(clientData.email, clientData.user!!)) {
                        null -> {
                            _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                            _alertDialog.value = AlertErrorOkData(
                                "Error",
                                "Se ha producido un error al crear el usuario de la aplicación. Revise los datos e inténtelo de nuevo. Recuerde que no se puede tener dos cuentas con el mismo correo y que el usuario debe tener, al menos, 6 caracteres.",
                                true
                            )
                        }
                        else -> {
                            val newUid: String = result
                            clientData.uid = newUid
                            val clientDataMap = ClientUtils.parcelableToMap(clientData)
                            when (val result =
                                updateFirestoreUserUseCase(clientDataMap, clientData.documentId!!)) {
                                false -> {
                                    _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                                    _alertDialog.value = AlertErrorOkData(
                                        "Error",
                                        "Se ha producido un error al guardar los cambios. Revise los datos e inténtelo de nuevo.",
                                        true
                                    )
                                }
                                true -> {
                                    _viewState.value = ModifyClientViewState(isLoading = false, error = false, correct = true)
                                }
                            }
                        }
                    }
                } else {
                    val clientDataMap = ClientUtils.parcelableToMap(clientData)
                    when (val result =
                        updateFirestoreUserUseCase(clientDataMap, clientData.documentId!!)) {
                        false -> {
                            _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                            _alertDialog.value = AlertErrorOkData(
                                "Error",
                                "Se ha producido un error cuando se estaban actualizado los datos del cliente. Por favor, revise los datos e inténtelo de nuevo.",
                                true
                            )
                        }
                        true -> {
                            _viewState.value = ModifyClientViewState(isLoading = false, error = false)
                            _alertDialog.value = AlertErrorOkData(
                                "Cliente actualizado",
                                "Los datos del cliente se han actualizado correctamente.",
                                true,
                                1
                            )
                        }
                    }
                }
            } else {
                _viewState.value = ModifyClientViewState(isLoading = true, error = true)
                _alertDialog.value = AlertErrorOkData(
                    "Correo no válido",
                    "Por favor, revise el formato del correo.",
                    true
                )
            }

        }
    }

}
