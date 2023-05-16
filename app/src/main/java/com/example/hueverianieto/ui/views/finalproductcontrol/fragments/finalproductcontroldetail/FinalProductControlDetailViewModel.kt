package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.finalproductcontroldetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.domain.usecases.DeleteFinalProductControlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalProductControlDetailViewModel @Inject constructor(
    val deleteFPCUseCase: DeleteFinalProductControlUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(FinalProductControlDetailViewState())
    val viewState: StateFlow<FinalProductControlDetailViewState> get() = _viewState

    fun deleteFinalProductControl(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FinalProductControlDetailViewState(isLoading = true)
            deleteFPCUseCase(documentId)
            _viewState.value = FinalProductControlDetailViewState(isLoading = false)
        }
    }

}
