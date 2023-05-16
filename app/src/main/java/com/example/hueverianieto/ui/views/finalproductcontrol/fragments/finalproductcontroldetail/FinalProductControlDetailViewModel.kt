package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.finalproductcontroldetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.domain.usecases.DeleteFinalProductControlUseCase
import com.example.hueverianieto.domain.usecases.GetFPCWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinalProductControlDetailViewModel @Inject constructor(
    val getFPCWithIdUseCase: GetFPCWithIdUseCase,
    val deleteFPCUseCase: DeleteFinalProductControlUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(FinalProductControlDetailViewState())
    val viewState: StateFlow<FinalProductControlDetailViewState> get() = _viewState

    private val _fpcData = MutableLiveData<FPCData?>()
    val fpcData: LiveData<FPCData?> get() = _fpcData

    fun getFinalProductControl(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FinalProductControlDetailViewState(isLoading = true)
            when(val result = getFPCWithIdUseCase(documentId)) {
                null -> _viewState.value = FinalProductControlDetailViewState(isLoading = false)
                else -> {
                    _viewState.value = FinalProductControlDetailViewState(isLoading = false)
                    _fpcData.value = result
                }
            }
        }
    }

    fun deleteFinalProductControl(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FinalProductControlDetailViewState(isLoading = true)
            deleteFPCUseCase(documentId)
            _viewState.value = FinalProductControlDetailViewState(isLoading = false)
        }
    }

    fun navigateToModifyFPCResources(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_finalProductControlDetailFragment_to_modifyFinalProductControlFragment, bundle)
            ?: Log.e(
                FinalProductControlDetailViewModel::class.simpleName,
                "Error en la navegación a modificar fpc"
            )
    }

}
