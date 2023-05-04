package com.example.hueverianieto.ui.views.clients.fragments.allclients

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
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
class AllClientsViewModel @Inject constructor(
    val getAllClientsUseCase: GetAllClientsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllClientsViewState())
    val viewState: StateFlow<AllClientsViewState> get() = _viewState

    private val _clientList = MutableLiveData<List<ClientData?>?>()
    val clientList: LiveData<List<ClientData?>?> get() = _clientList

    fun getClientsData() {

        // Se puede quitar este?
        _viewState.value = AllClientsViewState(isLoading = true)
        viewModelScope.launch {
            _viewState.value = AllClientsViewState(isLoading = true)
            when (val result = getAllClientsUseCase(false)) {
                null -> {
                    _viewState.value = AllClientsViewState(isLoading = false, error = true)
                    _clientList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value = AllClientsViewState(isLoading = false, isEmpty = false)
                    _clientList.value = listOf()
                }
                else -> {
                    _viewState.value = AllClientsViewState(isLoading = false)
                    _clientList.value = result
                }
            }
        }

    }

    fun navigateToClientDetails(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_allClientsFragment_to_clientDetailFragment, bundle)
            ?: Log.e(
                AllClientsFragment::class.simpleName,
                "Error en la navegación en newClientButton"
            )
    }

    fun navigateDeleteClients(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_allClientsFragment_to_deletedClientsFragment, bundle)
            ?: Log.e(
                AllClientsFragment::class.simpleName,
                "Error en la navegación a 'Clientes eliminados'"
            )
    }

}