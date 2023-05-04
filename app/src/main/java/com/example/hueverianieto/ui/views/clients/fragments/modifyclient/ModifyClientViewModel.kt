package com.example.hueverianieto.ui.views.clients.fragments.modifyclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.usecases.CreateAuthUserUseCase
import com.example.hueverianieto.domain.usecases.UpdateFirestoreUserUseCase
import com.example.hueverianieto.utils.ClientUtils
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

    fun updateUser(clientData: ClientData) {

        _viewState.value = ModifyClientViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = ModifyClientViewState(isLoading = true)
            if (clientData.hasAccount) {
                // TODO: Hay que llamar al primer servicio, guardar el uid en clientData y llamar al segundo servicio.
                when (val result = createAuthUserUseCase(clientData.email, clientData.user!!)) {
                    null -> {
                        _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                    }
                    else -> {
                        val newUid: String = result
                        clientData.uid = newUid
                        val clientDataMap = ClientUtils.parcelableToMap(clientData)
                        when (val result =
                            updateFirestoreUserUseCase(clientDataMap, clientData.documentId!!)) {
                            false -> {
                                _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                            }
                            true -> {
                                _viewState.value = ModifyClientViewState(isLoading = false, error = false)
                            }
                        }
                    }
                }
            } else {
                // TODO: Sólo hay que llamar al segundo servicio - cuidado con los datos que tienen que ver con hasaccount
                val clientDataMap = ClientUtils.parcelableToMap(clientData)
                when (val result =
                    updateFirestoreUserUseCase(clientDataMap, clientData.documentId!!)) {
                    false -> {
                        _viewState.value = ModifyClientViewState(isLoading = false, error = true)
                    }
                    true -> {
                        _viewState.value = ModifyClientViewState(isLoading = false, error = false)
                    }
                }
            }
        }
    }

}
