package com.example.hueverianieto.ui.views.workersresources.fragment.pendingworkersresources

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetAllInternalUsersUseCase
import com.example.hueverianieto.ui.views.workersresources.fragment.allworkersresources.AllWorkersResourcesViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingWorkersResourcesViewModel @Inject constructor(
    val getAllInternalUsersUseCase: GetAllInternalUsersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(PendingWorkersResourcesViewState())
    val viewState: StateFlow<PendingWorkersResourcesViewState> get() = _viewState

    private val _workerList = MutableLiveData<List<InternalUserData?>?>()
    val workerList: LiveData<List<InternalUserData?>?> get() = _workerList

    fun getAllWorkers() {
        viewModelScope.launch {
            _viewState.value = PendingWorkersResourcesViewState(isLoading = true)
            when (val result = getAllInternalUsersUseCase(false)) {
                null -> {
                    _viewState.value =
                        PendingWorkersResourcesViewState(isLoading = false, error = true)
                    _workerList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value =
                        PendingWorkersResourcesViewState(isLoading = false, isEmpty = false)
                    _workerList.value = listOf()
                }
                else -> {
                    _viewState.value = PendingWorkersResourcesViewState(isLoading = false)
                    _workerList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

    fun navigateToWorkerDetail(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_pendingWorkersResourcesFragment_to_workerDetailFragment, bundle)
            ?: Log.e(
                AllWorkersResourcesViewModel::class.simpleName,
                "Error en la navegación a trabajadores pendientes"
            )
    }

}
