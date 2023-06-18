package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.newfinalproductcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.domain.usecases.NewFinalProductControlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFinalProductControlViewModel @Inject constructor(
    val newFinalProductControlUseCase: NewFinalProductControlUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewFinalProductControlViewState())
    val viewState: StateFlow<NewFinalProductControlViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun addFPC(fpcData: FPCData) {
        viewModelScope.launch {
            _viewState.value = NewFinalProductControlViewState(isLoading = true)
            when (newFinalProductControlUseCase(fpcData)) {
                false -> {
                    _viewState.value =
                        NewFinalProductControlViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar el producto final. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = NewFinalProductControlViewState(isLoading = false)
                    _alertDialog.value = AlertOkData(
                        title = "FPC guardado",
                        text = "El producto final ha sido guardado correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
