package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.allelectricitywatergasresources

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.GetEWGUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllElectricityWaterGasViewModel @Inject constructor(
    val getEWGUseCase: GetEWGUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllElectricityWaterGasViewState())
    val viewState: StateFlow<AllElectricityWaterGasViewState> get() = _viewState

    private val _ewgList = MutableLiveData<List<ElectricityWaterGasResourcesData?>?>()
    val ewgList: LiveData<List<ElectricityWaterGasResourcesData?>?> get() = _ewgList

    fun getEWG() {
        viewModelScope.launch {
            _viewState.value = AllElectricityWaterGasViewState(isLoading = true)
            when(val result: List<ElectricityWaterGasResourcesData?>? = getEWGUseCase()) {
                null -> {
                    _viewState.value = AllElectricityWaterGasViewState(isLoading = false, error = true)
                    _ewgList.value = listOf()
                }
                listOf<HensResourcesData>() -> {
                    _viewState.value = AllElectricityWaterGasViewState(isLoading = false, error = false)
                    _ewgList.value = listOf()
                }
                else -> {
                    _viewState.value = AllElectricityWaterGasViewState(isLoading = false, error = false)
                    _ewgList.value = result.sortedBy { it?.expenseDatetime }.reversed()
                }
            }
        }
    }

    fun navigateToEWGResourcesDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_allElectricityWaterGasFragment_to_electricityWaterGasResourcesDetail, bundle)
            ?: Log.e(
                AllElectricityWaterGasViewModel::class.simpleName,
                "Error en la navegación a detalle de ticket de EWG"
            )
    }

}