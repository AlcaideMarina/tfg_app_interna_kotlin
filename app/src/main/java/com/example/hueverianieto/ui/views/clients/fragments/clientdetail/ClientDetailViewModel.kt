package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import androidx.lifecycle.ViewModel
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

}
