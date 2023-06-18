package com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail

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
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.DeleteHensResourcesUseCase
import com.example.hueverianieto.domain.usecases.GetHenResourcesWithIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HensResourcesDetailViewModel @Inject constructor(
    val getHenResourcesWithIdUseCase: GetHenResourcesWithIdUseCase,
    val deleteHensResourcesUseCase: DeleteHensResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(HensResourcesDetailViewState())
    val viewState: StateFlow<HensResourcesDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private val _henResource = MutableLiveData<HensResourcesData?>()
    val henResource: LiveData<HensResourcesData?> get() = _henResource

    fun getHensResource(documentId: String) {
        viewModelScope.launch {
            _viewState.value = HensResourcesDetailViewState(isLoading = true)
            when (val result = getHenResourcesWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = HensResourcesDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = HensResourcesDetailViewState(isLoading = false)
                    _henResource.value = result

                }
            }
        }
    }

    fun deleteHenResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = HensResourcesDetailViewState(isLoading = true)
            deleteHensResourcesUseCase(documentId)
            _alertDialog.value = AlertOkData(
                "Recurso eliminado",
                "El recurso ha sido eliminado correctamente.",
                true
            )
            _viewState.value = HensResourcesDetailViewState(isLoading = false)
        }
    }

    fun navigateToModifyHensResources(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_hensResourcesDetailFragment_to_modifyHensResourcesFragment,
            bundle
        )
            ?: Log.e(
                HensResourcesDetailViewModel::class.simpleName,
                "Error en la navegación a modificar ticket de gallinas"
            )
    }

}
