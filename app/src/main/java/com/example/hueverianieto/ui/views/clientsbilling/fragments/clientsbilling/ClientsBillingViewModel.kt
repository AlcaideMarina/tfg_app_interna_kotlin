package com.example.hueverianieto.ui.views.clientsbilling.fragments.clientsbilling

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
import com.example.hueverianieto.domain.usecases.GetAllClientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientsBillingViewModel @Inject constructor(
    val getAllClientsUseCase: GetAllClientsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ClientsBillingViewState())
    val viewState: StateFlow<ClientsBillingViewState> get() = _viewState

    private val _clientList = MutableLiveData<List<ClientData?>?>()
    val clientList: LiveData<List<ClientData?>?> get() = _clientList

    fun getClientsData() {
        viewModelScope.launch {
            _viewState.value = ClientsBillingViewState(isLoading = true)
            when (val result : List<ClientData?>? = getAllClientsUseCase(false)) {
                null -> {
                    _viewState.value = ClientsBillingViewState(isLoading = false, error = true)
                    _clientList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value = ClientsBillingViewState(isLoading = false, isEmpty = false)
                    _clientList.value = listOf()
                }
                else -> {
                    _viewState.value = ClientsBillingViewState(isLoading = false)
                    _clientList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

    fun navigateToClientBillingPerMonth(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_clientsBillingFragment_to_billingPerMonthFragment, bundle)
            ?: Log.e(
                ClientsBillingViewModel::class.simpleName,
                "Error en la navegación a facturación mensual"
            )
    }

}
