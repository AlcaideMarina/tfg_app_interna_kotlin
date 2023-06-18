package com.example.hueverianieto.ui.views.hensresouces.fragments.newhensresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.NewHensResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewHensResourcesViewModel @Inject constructor(
    val newHensResourcesUseCase: NewHensResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewHensResourcesViewState())
    val viewState: StateFlow<NewHensResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addHensResource(hensResourcesData: HensResourcesData) {
        viewModelScope.launch {
            _viewState.value = NewHensResourcesViewState(isLoading = true)
            when (newHensResourcesUseCase(hensResourcesData)) {
                false -> {
                    _viewState.value = NewHensResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar el recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = NewHensResourcesViewState(isLoading = false)
                    _alertDialog.value = AlertOkData(
                        title = "Recurso guardado",
                        text = "La información sobre el recurso ha sido actualizada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
