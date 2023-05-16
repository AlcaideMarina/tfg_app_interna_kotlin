package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.modifyfinalproductcontrol

import android.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.domain.usecases.UpdateFPCUseCase
import com.example.hueverianieto.utils.FarmUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyFinalProductControlViewModel @Inject constructor(
    val updateFPCUseCase: UpdateFPCUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyFinalProductControlViewState())
    val viewState: StateFlow<ModifyFinalProductControlViewState> get() = _viewState

    private val _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateFPC(fpcData: FPCData) {
        viewModelScope.launch {
            _viewState.value = ModifyFinalProductControlViewState(isLoading = true)
            val hensResourcesMap = FarmUtils.fpcParcelableToMap(fpcData)
            when(val result = updateFPCUseCase(hensResourcesMap, fpcData.documentId!!)) {
                false -> {
                    _viewState.value = ModifyFinalProductControlViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del producto final. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyFinalProductControlViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        "FPC actualizado",
                        "Los datos del producto final se han actualizado correctamente.",
                        true,
                        0
                    )
                }
            }
        }
    }

}
