package com.example.hueverianieto.ui.views.internalusers.fragments.newinternaluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.CreateAuthUserUseCase
import com.example.hueverianieto.domain.usecases.GetInternalUserIdUseCase
import com.example.hueverianieto.domain.usecases.NewInternalUserUseCase
import com.example.hueverianieto.ui.views.clients.fragments.newclient.NewClientViewState
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewInternalUserViewModel @Inject constructor(
    val newInternalUserUseCase: NewInternalUserUseCase,
    val getInternalUserIdUseCase: GetInternalUserIdUseCase,
    val createAuthUserUseCase: CreateAuthUserUseCase
) :  ViewModel() {

    private val _viewState = MutableStateFlow(NewInternalUserViewState())
    val viewState: StateFlow<NewInternalUserViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog : LiveData<AlertOkData> get() = _alertDialog

    fun addNewInternalUser(internalUserData: InternalUserData) {
        viewModelScope.launch {
            _viewState.value = NewInternalUserViewState(isLoading = true)
            if (Utils.isValidEmail(internalUserData.email)) {
                when(val newUid = createAuthUserUseCase(internalUserData.email, internalUserData.user)) {
                    null -> {
                        _viewState.value = NewInternalUserViewState(isLoading = false, error = true)
                        _alertDialog.value = AlertOkData(
                            "Error",
                            "Se ha producido un error al crear el usuario de la aplicación. Revise los datos e inténtelo de nuevo. Recuerde que no se puede tener dos cuentas con el mismo correo y que el usuario debe tener, al menos, 6 caracteres.",
                            true
                        )
                    }
                    else -> {
                        internalUserData.uid = newUid
                        when(val internalUserId = getInternalUserIdUseCase()) {
                            null -> {
                                _viewState.value = NewInternalUserViewState(isLoading = false, error = true)
                            }
                            else -> {
                                internalUserData.id = internalUserId
                                when(newInternalUserUseCase(internalUserData)) {
                                    false -> {
                                        _viewState.value = NewInternalUserViewState(
                                            isLoading = false,
                                            error = true)
                                        _alertDialog.value = AlertOkData(
                                            title = "Error",
                                            text = "Se ha producido un error al guardar el nuevo cliente. Por favor, revise los datos e inténtelo de nuevo.",
                                            true
                                        )
                                    }
                                    true -> {
                                        _viewState.value = NewInternalUserViewState(
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
                _viewState.value = NewInternalUserViewState(isLoading = false, error = true)
                _alertDialog.value = AlertOkData(
                    title = "Correo no válido",
                    text = "El correo introducido no tiene un formato válido. Por favor, revise los datos e inténtelo de nuevo.",
                    true
                )
            }


        }
    }

}