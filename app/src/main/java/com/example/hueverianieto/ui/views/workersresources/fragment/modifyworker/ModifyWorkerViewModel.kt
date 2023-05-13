package com.example.hueverianieto.ui.views.workersresources.fragment.modifyworker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.UpdateFirestoreUserUseCase
import com.example.hueverianieto.ui.views.internalusers.fragments.modifyinternaluser.ModifyInternalUserViewState
import com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail.WorkerDetailViewState
import com.example.hueverianieto.utils.InternalUserUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyWorkerViewModel @Inject constructor(
    val updateFirestoreUserUseCase: UpdateFirestoreUserUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyWorkerViewState())
    val viewState: StateFlow<ModifyWorkerViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateUser(internalUserData: InternalUserData) {
        viewModelScope.launch {
            _viewState.value = ModifyWorkerViewState(isLoading = true)
            if(Utils.isValidEmail(internalUserData.email)) {
                val internalUserDataMap = InternalUserUtils.parcelableToMap(internalUserData)
                when (val result = updateFirestoreUserUseCase(
                    internalUserDataMap, internalUserData.documentId!!, "user_info")) {
                    false -> {
                        _viewState.value = ModifyWorkerViewState(isLoading = false, error = true)
                        _alertDialog.value = AlertOkData(
                            "Error",
                            "Se ha producido un error cuando se estaban actualizado los datos del cliente. Por favor, revise los datos e inténtelo de nuevo.",
                            true
                        )
                    }
                    true -> {
                        _viewState.value = ModifyWorkerViewState(isLoading = false, error = false)
                        _alertDialog.value = AlertOkData(
                            "Sueldo actualizado",
                            "El sueldo del trabajador ha sido modificado correctamente.",
                            true,
                            1
                        )
                    }
                }
            }
        }
    }

}
