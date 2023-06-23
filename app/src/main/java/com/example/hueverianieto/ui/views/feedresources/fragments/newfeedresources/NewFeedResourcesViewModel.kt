package com.example.hueverianieto.ui.views.feedresources.fragments.newfeedresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.domain.usecases.NewFeedResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFeedResourcesViewModel @Inject constructor(
    val newFeedResourcesUseCase: NewFeedResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewFeedResourcesViewState())
    val viewState: StateFlow<NewFeedResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addFeedResource(feedResourcesData: FeedResourcesData) {
        viewModelScope.launch {
            _viewState.value = NewFeedResourcesViewState(isLoading = true)
            when (newFeedResourcesUseCase(feedResourcesData)) {
                false -> {
                    _viewState.value = NewFeedResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar el recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = NewFeedResourcesViewState(isLoading = false)
                    _alertDialog.value = AlertOkData(
                        title = "Recurso guardado",
                        text = "Las información sobre el pienso han sido guardada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
