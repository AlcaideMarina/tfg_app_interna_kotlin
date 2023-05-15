package com.example.hueverianieto.ui.views.feedresources.fragments.modifyfeedresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.domain.usecases.UpdateFeedUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.modifyhensresources.ModifyHensResourcesViewState
import com.example.hueverianieto.utils.MaterialUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyFeedResourcesViewModel @Inject constructor(
    val updateFeedUseCase: UpdateFeedUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyFeedResourcesViewState())
    val viewState: StateFlow<ModifyFeedResourcesViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateFeed(feedResourcesData: FeedResourcesData) {
        viewModelScope.launch {
            _viewState.value = ModifyFeedResourcesViewState(isLoading = true)
            val hensResourcesMap = MaterialUtils.feedParcelableToMap(feedResourcesData)
            when(val result = updateFeedUseCase(hensResourcesMap, feedResourcesData.documentId!!)) {
                false -> {
                    _viewState.value = ModifyFeedResourcesViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del recurso. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyFeedResourcesViewState(isLoading = false, error = false)
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
