package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.ClientDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ClientDetailViewModel @Inject constructor(
    val clientDetailUseCase: ClientDetailUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ClientDetailViewState())
    val viewState: StateFlow<ClientDetailViewState> get() = _viewState

    fun navigateToModifyClient(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_clientDetailFragment_to_modifyClientFragment, bundle)
            ?: Log.e(
                ClientDetailViewModel::class.simpleName,
                "Error en la navegación a Modificar cliente"
            )
    }

}
