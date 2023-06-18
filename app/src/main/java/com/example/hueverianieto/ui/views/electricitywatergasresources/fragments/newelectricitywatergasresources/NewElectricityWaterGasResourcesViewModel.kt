package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.newelectricitywatergasresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.domain.usecases.NewElectricityWaterGasResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewElectricityWaterGasResourcesViewModel @Inject constructor(
    val newElectricityWaterGasResourcesUseCase: NewElectricityWaterGasResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewElectricityWaterGasResourcesViewState())
    val viewState: StateFlow<NewElectricityWaterGasResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addEWGResource(electricityWaterGasResourcesData: ElectricityWaterGasResourcesData) {
        viewModelScope.launch {
            _viewState.value = NewElectricityWaterGasResourcesViewState(isLoading = true)
            when (newElectricityWaterGasResourcesUseCase(electricityWaterGasResourcesData)) {
                false -> {
                    _viewState.value =
                        NewElectricityWaterGasResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar el recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = NewElectricityWaterGasResourcesViewState(isLoading = false)
                    _alertDialog.value = AlertOkData(
                        title = "Recurso guardado",
                        text = "La información sobre luz, agua y gas han sido guardada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
