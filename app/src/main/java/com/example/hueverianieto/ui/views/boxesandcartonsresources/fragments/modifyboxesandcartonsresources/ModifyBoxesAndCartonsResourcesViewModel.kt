package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.modifyboxesandcartonsresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.domain.usecases.UpdateBoxesAndCartonsUseCase
import com.example.hueverianieto.ui.views.feedresources.fragments.modifyfeedresources.ModifyFeedResourcesViewState
import com.example.hueverianieto.utils.MaterialUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyBoxesAndCartonsResourcesViewModel @Inject constructor(
    val updateBoxesAndCartonsUseCase: UpdateBoxesAndCartonsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyBoxesAndCartonsResourcesViewState())
    val viewState: StateFlow<ModifyBoxesAndCartonsResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateBoxesAndCartons(bcResourcesData: BoxesAndCartonsResourcesData) {
        viewModelScope.launch {
            _viewState.value = ModifyBoxesAndCartonsResourcesViewState(isLoading = true)
            val hensResourcesMap = MaterialUtils.boxesAndCartonsParcelableToMap(bcResourcesData)
            when(val result = updateBoxesAndCartonsUseCase(hensResourcesMap, bcResourcesData.documentId!!)) {
                false -> {
                    _viewState.value = ModifyBoxesAndCartonsResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyBoxesAndCartonsResourcesViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        "Cliente actualizado",
                        "Los datos del recurso se han actualizado correctamente.",
                        true,
                        0
                    )
                }
            }
        }
    }

}
