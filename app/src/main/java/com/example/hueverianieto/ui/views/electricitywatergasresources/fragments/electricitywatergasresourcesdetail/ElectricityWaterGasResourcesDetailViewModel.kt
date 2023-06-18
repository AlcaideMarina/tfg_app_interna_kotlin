package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.electricitywatergasresourcesdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.domain.usecases.DeleteEWGResourcesUseCase
import com.example.hueverianieto.domain.usecases.GetEWGResourcesWithIdUseCase
import com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.allelectricitywatergasresources.AllElectricityWaterGasViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectricityWaterGasResourcesDetailViewModel @Inject constructor(
    val getEWGResourcesWithIdUseCase: GetEWGResourcesWithIdUseCase,
    val deleteEWGUseCase: DeleteEWGResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ElectricityWaterGasResourcesDetailViewState())
    val viewState: StateFlow<ElectricityWaterGasResourcesDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private val _ewgResource = MutableLiveData<ElectricityWaterGasResourcesData?>()
    val ewgResource: LiveData<ElectricityWaterGasResourcesData?> get() = _ewgResource

    fun getEWGResource(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = true)
            when (val result = getEWGResourcesWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value =
                        ElectricityWaterGasResourcesDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value =
                        ElectricityWaterGasResourcesDetailViewState(isLoading = false)
                    _ewgResource.value = result

                }
            }
        }
    }

    fun deleteEWGResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = true)
            deleteEWGUseCase(documentId)
            _alertDialog.value = AlertOkData(
                "Recurso eliminado",
                "El recurso ha sido eliminado correctamente.",
                true
            )
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = false)
        }
    }

    fun navigateToModifyEWGResources(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_electricityWaterGasResourcesDetail_to_modifyElectricityWaterGasResourcesFragment,
            bundle
        )
            ?: Log.e(
                AllElectricityWaterGasViewModel::class.simpleName,
                "Error en la navegación a modificar el ticket de EWG"
            )
    }

}
