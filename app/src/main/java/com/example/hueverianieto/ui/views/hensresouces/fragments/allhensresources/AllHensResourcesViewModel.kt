package com.example.hueverianieto.ui.views.hensresouces.fragments.allhensresources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.workersresources.fragment.allworkersresources.AllWorkersResourcesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AllHensResourcesViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllHensResourcesViewState())
    val viewState: StateFlow<AllHensResourcesViewState> get() = _viewState

    private val _workerList = MutableLiveData<List<InternalUserData?>?>()
    val workerList: LiveData<List<InternalUserData?>?> get() = _workerList

}