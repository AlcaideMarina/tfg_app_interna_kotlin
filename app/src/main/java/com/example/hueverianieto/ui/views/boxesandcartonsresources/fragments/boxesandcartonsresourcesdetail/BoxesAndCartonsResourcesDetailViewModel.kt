package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.boxesandcartonsresourcesdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.domain.usecases.DeleteBoxesAndCartonsResourcesUseCase
import com.example.hueverianieto.domain.usecases.GetBoxesAndCartonsResourcesWithIdUseCase
import com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces.AllFeedResourcesViewModel
import com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail.FeedResourcesDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxesAndCartonsResourcesDetailViewModel @Inject constructor(
    val getBoxesAndCartonsResourcesWithIdUseCase: GetBoxesAndCartonsResourcesWithIdUseCase,
    val deleteBoxesAndCartonsResourcesUseCase: DeleteBoxesAndCartonsResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(BoxesAndCartonsResourcesDetailViewState())
    val viewState: StateFlow<BoxesAndCartonsResourcesDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private val _bcResource = MutableLiveData<BoxesAndCartonsResourcesData?>()
    val bcResource: LiveData<BoxesAndCartonsResourcesData?> get() = _bcResource

    fun getBoxesAndCartonsResource(documentId: String) {
        viewModelScope.launch {
            _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = true)
            when(val result = getBoxesAndCartonsResourcesWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = false)
                    _bcResource.value = result

                }
            }
        }
    }

    fun deleteBoxesAndCartonsResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = true)
            deleteBoxesAndCartonsResourcesUseCase(documentId)
            _alertDialog.value = AlertOkData(
                "Recurso eliminado",
                "El recurso ha sido eliminado correctamente.",
                true
            )
            _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = false)
        }
    }

    fun navigationToModifyBCResources(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_boxesAndCartonsResourcesDetailFragment_to_modifyBoxesAndCartonsResourcesFragment, bundle)
            ?: Log.e(
                AllFeedResourcesViewModel::class.simpleName,
                "Error en la navegación a detalle de recursos (pienso)"
            )
    }

}
