package com.example.hueverianieto.ui.views.hensresouces.fragments.modifyhensresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.services.UpdateFirestoreService
import com.example.hueverianieto.domain.usecases.UpdateHensUseCase
import com.example.hueverianieto.ui.views.clients.fragments.modifyclient.ModifyClientViewState
import com.example.hueverianieto.utils.MaterialUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyHensResourcesViewModel @Inject constructor(
    val updateHensUseCase: UpdateHensUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyHensResourcesViewState())
    val viewState: StateFlow<ModifyHensResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateHens(hensResourcesData: HensResourcesData) {
        viewModelScope.launch {
            _viewState.value = ModifyHensResourcesViewState(isLoading = true)
            val hensResourcesMap = MaterialUtils.hensParcelableToMap(hensResourcesData)
            when(val result = updateHensUseCase(hensResourcesMap, hensResourcesData.documentId!!)) {
                false -> {
                    _viewState.value = ModifyHensResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del cliente. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyHensResourcesViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        "Cliente actualizado",
                        "Los datos del cliente se han actualizado correctamente.",
                        true,
                        0
                    )
                }
            }
        }
    }

}
