package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.electricitywatergasresourcesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.DeleteEWGResourcesUseCase
import com.example.hueverianieto.domain.usecases.GetEWGResourcesWithIdUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailViewState
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

    private val _ewgResource = MutableLiveData<ElectricityWaterGasResourcesData?>()
    val ewgResource: LiveData<ElectricityWaterGasResourcesData?> get() = _ewgResource

    fun getEWGResource(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = true)
            when(val result = getEWGResourcesWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = false)
                    _ewgResource.value = result

                }
            }
        }
    }

    fun deleteEWGResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = true)
            deleteEWGUseCase(documentId)
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = false)
        }
    }

}
