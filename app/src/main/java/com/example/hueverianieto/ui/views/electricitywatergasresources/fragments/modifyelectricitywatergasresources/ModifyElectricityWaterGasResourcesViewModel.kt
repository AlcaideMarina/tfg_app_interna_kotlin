package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.modifyelectricitywatergasresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.domain.usecases.UpdateEWGUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.modifyhensresources.ModifyHensResourcesViewState
import com.example.hueverianieto.utils.MaterialUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyElectricityWaterGasResourcesViewModel @Inject constructor(
    val updateEWGUseCase: UpdateEWGUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyElectricityWaterGasResourcesViewState())
    val viewState: StateFlow<ModifyElectricityWaterGasResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateEWG(ewgResourcesData: ElectricityWaterGasResourcesData) {
        viewModelScope.launch {
            _viewState.value = ModifyElectricityWaterGasResourcesViewState(isLoading = true)
            val hensResourcesMap = MaterialUtils.ewgParcelableToMap(ewgResourcesData)
            when(val result = updateEWGUseCase(hensResourcesMap, ewgResourcesData.documentId!!)) {
                false -> {
                    _viewState.value = ModifyElectricityWaterGasResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del cliente. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyElectricityWaterGasResourcesViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        "Cliente actualizado",
                        "Los datos del cliente se han actualizado correctamente.",
                        true,
                        0
                    )
                }
            }
        }
    }

}
