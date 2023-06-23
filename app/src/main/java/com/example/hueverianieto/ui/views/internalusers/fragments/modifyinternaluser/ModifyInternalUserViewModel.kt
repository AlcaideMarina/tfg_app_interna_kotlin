package com.example.hueverianieto.ui.views.internalusers.fragments.modifyinternaluser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.UpdateFirestoreUserUseCase
import com.example.hueverianieto.utils.InternalUserUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyInternalUserViewModel @Inject constructor(
    private val updateFirestoreUserUseCase: UpdateFirestoreUserUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyInternalUserViewState())
    val viewState: StateFlow<ModifyInternalUserViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateUser(internalUserData: InternalUserData) {
        viewModelScope.launch {
            _viewState.value = ModifyInternalUserViewState(isLoading = true)
            if (Utils.isValidEmail(internalUserData.email)) {
                val internalUserDataMap = InternalUserUtils.parcelableToMap(internalUserData)
                when (val result = updateFirestoreUserUseCase(
                    internalUserDataMap, internalUserData.documentId!!, "user_info"
                )) {
                    false -> {
                        _viewState.value =
                            ModifyInternalUserViewState(isLoading = false, error = true)
                        _alertDialog.value = AlertOkData(
                            "Error",
                            "Se ha producido un error cuando se estaban actualizado los datos del cliente. Por favor, revise los datos e inténtelo de nuevo.",
                            true
                        )
                    }
                    true -> {
                        _viewState.value =
                            ModifyInternalUserViewState(isLoading = false, error = false)
                        _alertDialog.value = AlertOkData(
                            "Cliente actualizado",
                            "Los datos del cliente se han actualizado correctamente.",
                            true,
                            1
                        )
                    }
                }
            }
        }
    }

}
