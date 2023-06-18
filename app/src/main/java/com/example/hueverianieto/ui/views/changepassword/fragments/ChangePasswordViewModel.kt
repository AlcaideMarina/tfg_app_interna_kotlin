package com.example.hueverianieto.ui.views.changepassword.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.domain.usecases.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    val changePasswordUseCase: ChangePasswordUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow(ChangePasswordViewState())
    val viewState: StateFlow<ChangePasswordViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun checkOldPassword(oldPass: String, newPass: String) {
        viewModelScope.launch {
            _viewState.value = ChangePasswordViewState(isLoading = true)
            when (changePasswordUseCase(oldPass, newPass)) {
                false -> {
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _alertDialog.value = AlertOkData(
                        "Contraseña actualizada",
                        "La información se ha actualizado correctamente. A partir de ahora, cuando inicie sesión, hágalo con la nueva contraseña.",
                        true,
                        customCode = 0
                    )
                }
            }
            _viewState.value = ChangePasswordViewState(isLoading = false)
        }
    }

}
