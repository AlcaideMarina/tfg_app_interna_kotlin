package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.newboxesandcartonsresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.domain.usecases.NewBoxesAndCartonsResourcesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewBoxesAndCartonsResourcesViewModel @Inject constructor(
    val newBoxesAndCartonsResourcesUseCase: NewBoxesAndCartonsResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewBoxesAndCartonsResourcesViewState())
    val viewState: StateFlow<NewBoxesAndCartonsResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addBoxesAndCartonsResource(bcResourcesData: BoxesAndCartonsResourcesData) {
        viewModelScope.launch {
            _viewState.value = NewBoxesAndCartonsResourcesViewState(isLoading = true)
            when (newBoxesAndCartonsResourcesUseCase(bcResourcesData)) {
                false -> {
                    _viewState.value =
                        NewBoxesAndCartonsResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar el recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = NewBoxesAndCartonsResourcesViewState(isLoading = false)
                    _alertDialog.value = AlertOkData(
                        title = "Recurso guardado",
                        text = "La información del recurso ha sido guardada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
