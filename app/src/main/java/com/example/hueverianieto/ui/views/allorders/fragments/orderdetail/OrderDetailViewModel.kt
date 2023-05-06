package com.example.hueverianieto.ui.views.allorders.fragments.orderdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.usecases.GetClientWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(
    val getClientWithIdUseCase: GetClientWithIdUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(OrderDetailViewState())
    val viewState: StateFlow<OrderDetailViewState> get() = _viewState

    private var _clientData = MutableLiveData<ClientData?>()
    val clientData: LiveData<ClientData?> get() = _clientData

    fun getClientData(clientId: Long) {
        _viewState.value = OrderDetailViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = OrderDetailViewState(isLoading = true)
            when (val result = getClientWithIdUseCase(clientId)) {
                null -> {
                    _viewState.value = OrderDetailViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = OrderDetailViewState(isLoading = false)
                    _clientData.value = result!!
                }
            }
        }
    }

}