package com.example.hueverianieto.ui.views.internalusers.fragments.internaluserdetail

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
import com.example.hueverianieto.domain.usecases.DeleteInternalUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternalUserDetailViewModel @Inject constructor(
    val deleteInternalUserUseCase : DeleteInternalUserUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(InternalUserDetailViewState())
    val viewState: StateFlow<InternalUserDetailViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun deleteInternalUser(documentId: String) {
        viewModelScope.launch {
            _viewState.value = InternalUserDetailViewState(isLoading = true)
            when(val result = deleteInternalUserUseCase(documentId)) {
                false -> {
                    _viewState.value = InternalUserDetailViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error al eliminar el cliente. Revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = InternalUserDetailViewState(isLoading = false, error = false, correct = true)
                    _alertDialog.value = AlertOkData(
                        "Cliente eliminado",
                        "El cliente ha sido eliminado correctamente",
                        true,
                        0
                    )
                }
            }
        }
    }

    fun navigateToModifyInternalUser(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_internalUserDetailFragment_to_modifyInternalUserFragment, bundle)
            ?: Log.e(
                InternalUserDetailViewModel::class.simpleName,
                "Error en la navegación a Modificar usuario interno"
            )
    }

}