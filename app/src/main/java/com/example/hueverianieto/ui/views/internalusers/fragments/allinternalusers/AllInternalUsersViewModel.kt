package com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetAllInternalUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllInternalUsersViewModel @Inject constructor(
    val getAllInternalUsersUseCase: GetAllInternalUsersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllInternalUsersViewState())
    val viewState: StateFlow<AllInternalUsersViewState> get() = _viewState

    private val _internalUserList = MutableLiveData<List<InternalUserData?>?>()
    val internalUserList: LiveData<List<InternalUserData?>?> get() = _internalUserList

    fun getInternalUserData() {
        viewModelScope.launch {
            _viewState.value = AllInternalUsersViewState(isLoading = true)
            when (val result: List<InternalUserData?>? = getAllInternalUsersUseCase(false)) {
                null -> {
                    _viewState.value = AllInternalUsersViewState(isLoading = false, error = true)
                    _internalUserList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value = AllInternalUsersViewState(isLoading = false, isEmpty = false)
                    _internalUserList.value = listOf()
                }
                else -> {
                    _viewState.value = AllInternalUsersViewState(isLoading = false)
                    _internalUserList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

    fun navigateDeleteInternalUsers(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_allInternalUsersFragment_to_deletedInternalUsersFragment, bundle)
            ?: Log.e(
                AllInternalUsersViewModel::class.simpleName,
                "Error en la navegación a Usuarios internos eliminados"
            )
    }

    fun navigateToNewInternalUsers(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_allInternalUsersFragment_to_newInternalUserFragment, bundle)
            ?: Log.e(
                AllInternalUsersViewModel::class.simpleName,
                "Error en la navegación a Usuarios internos eliminados"
            )
    }

    fun navigateToInternalUserDetail(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_allInternalUsersFragment_to_internalUserDetailFragment, bundle)
            ?: Log.e(
                AllInternalUsersViewModel::class.simpleName,
                "Error en la navegacion a Detalle de usuario interno"
            )
    }

}
