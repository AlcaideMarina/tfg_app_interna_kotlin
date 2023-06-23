package com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetInternalUserWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkerDetailViewModel @Inject constructor(
    val getInternalUserWithIdUseCase: GetInternalUserWithIdUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(WorkerDetailViewState())
    val viewState: StateFlow<WorkerDetailViewState> get() = _viewState

    private val _worker = MutableLiveData<InternalUserData?>()
    val worker: LiveData<InternalUserData?> get() = _worker

    fun getInternalUser(documentId: String) {
        viewModelScope.launch {
            _viewState.value = WorkerDetailViewState(isLoading = true)
            when (val result = getInternalUserWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = WorkerDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = WorkerDetailViewState(isLoading = false)
                    _worker.value = result

                }
            }
        }
    }

    fun navigateToModifyWorker(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_workerDetailFragment_to_modifyWorkerFragment, bundle)
            ?: Log.e(
                WorkerDetailViewModel::class.simpleName,
                "Error en la navegación a modificar trabajador"
            )
    }

}
