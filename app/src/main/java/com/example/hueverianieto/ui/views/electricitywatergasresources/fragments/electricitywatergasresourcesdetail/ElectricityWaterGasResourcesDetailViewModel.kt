package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.electricitywatergasresourcesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.domain.usecases.DeleteEWGResourcesUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectricityWaterGasResourcesDetailViewModel @Inject constructor(
    val deleteEWGUseCase: DeleteEWGResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ElectricityWaterGasResourcesDetailViewState())
    val viewState: StateFlow<ElectricityWaterGasResourcesDetailViewState> get() = _viewState

    fun deleteEWGResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = true)
            deleteEWGUseCase(documentId)
            _viewState.value = ElectricityWaterGasResourcesDetailViewState(isLoading = false)
        }
    }

}
