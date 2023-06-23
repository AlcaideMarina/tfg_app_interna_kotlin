package com.example.hueverianieto.ui.views.internalusers.fragments.deletedinternalusers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetAllInternalUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletedInternalUsersViewModel @Inject constructor(
    private val getAllInternalUsersUseCase: GetAllInternalUsersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(DeletedInternalUsersViewState())
    val viewState: StateFlow<DeletedInternalUsersViewState> get() = _viewState

    private val _internalUserList = MutableLiveData<List<InternalUserData?>?>()
    val internalUserList: LiveData<List<InternalUserData?>?> get() = _internalUserList

    fun getInternalUserData() {
        viewModelScope.launch {
            _viewState.value = DeletedInternalUsersViewState(isLoading = true)
            when (val result: List<InternalUserData?>? = getAllInternalUsersUseCase(true)) {
                null -> {
                    _viewState.value =
                        DeletedInternalUsersViewState(isLoading = false, error = true)
                    _internalUserList.value = listOf()
                }
                listOf<ClientData>() -> {
                    _viewState.value =
                        DeletedInternalUsersViewState(isLoading = false, isEmpty = false)
                    _internalUserList.value = listOf()
                }
                else -> {
                    _viewState.value = DeletedInternalUsersViewState(isLoading = false)
                    _internalUserList.value = result.sortedBy { it?.id }
                }
            }
        }
    }

}
