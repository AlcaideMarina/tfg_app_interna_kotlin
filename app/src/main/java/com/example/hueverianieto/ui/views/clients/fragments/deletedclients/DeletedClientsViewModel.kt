package com.example.hueverianieto.ui.views.clients.fragments.deletedclients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.usecases.GetAllClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletedClientsViewModel @Inject constructor(
    private val getAllClientsUseCase: GetAllClientsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(DeletedClientsViewState())
    val viewState: StateFlow<DeletedClientsViewState> get() = _viewState

    private val _clientList = MutableLiveData<List<ClientData?>?>()
    val clientList: LiveData<List<ClientData?>?> get() = _clientList

    fun getClientsListData() {
        _viewState.value = DeletedClientsViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = DeletedClientsViewState(isLoading = true)
            when (val result = getAllClientsUseCase(true)) {
                null -> {
                    _viewState.value = DeletedClientsViewState(isLoading = false, error = true)
                    _clientList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value = DeletedClientsViewState(isLoading = false, isEmpty = false)
                    _clientList.value = listOf()
                }
                else -> {
                    _viewState.value = DeletedClientsViewState(isLoading = false)
                    _clientList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

}
