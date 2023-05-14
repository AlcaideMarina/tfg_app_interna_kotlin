package com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.GetFeedUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.allhensresources.AllHensResourcesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFeedResourcesViewModel @Inject constructor(
    val getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllFeedResourcesViewState())
    val viewState: StateFlow<AllFeedResourcesViewState> get() = _viewState

    private val _feedList = MutableLiveData<List<FeedResourcesData?>?>()
    val feedList: LiveData<List<FeedResourcesData?>?> get() = _feedList

    fun getFeed() {
        viewModelScope.launch {
            viewModelScope.launch {
                _viewState.value = AllFeedResourcesViewState(isLoading = true)
                when(val result: List<FeedResourcesData?>? = getFeedUseCase()) {
                    null -> {
                        _viewState.value = AllFeedResourcesViewState(isLoading = false, error = true)
                        _feedList.value = listOf()
                    }
                    listOf<HensResourcesData>() -> {
                        _viewState.value = AllFeedResourcesViewState(isLoading = false, error = false)
                        _feedList.value = listOf()
                    }
                    else -> {
                        _viewState.value = AllFeedResourcesViewState(isLoading = false, error = false)
                        _feedList.value = result.sortedBy { it?.expenseDatetime }.reversed()
                    }
                }
            }
        }
    }

}