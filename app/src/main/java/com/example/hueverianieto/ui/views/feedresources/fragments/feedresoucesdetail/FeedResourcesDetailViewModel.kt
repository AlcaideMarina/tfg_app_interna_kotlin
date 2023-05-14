package com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.DeleteFeedResourcesUseCase
import com.example.hueverianieto.domain.usecases.GetFeedResourcesWithIdUseCase
import com.example.hueverianieto.domain.usecases.GetFeedUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedResourcesDetailViewModel @Inject constructor(
    val getFeedResourcesWithIdUseCase: GetFeedResourcesWithIdUseCase,
    val deleteFeedResourcesUseCase: DeleteFeedResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(FeedResourcesDetailViewState())
    val viewState: StateFlow<FeedResourcesDetailViewState> get() = _viewState

    private val _feedResource = MutableLiveData<FeedResourcesData?>()
    val feedResource: LiveData<FeedResourcesData?> get() = _feedResource

    fun getFeedResource(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FeedResourcesDetailViewState(isLoading = true)
            when(val result = getFeedResourcesWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = FeedResourcesDetailViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = FeedResourcesDetailViewState(isLoading = false)
                    _feedResource.value = result

                }
            }
        }
    }

    fun deleteFeedResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FeedResourcesDetailViewState(isLoading = true)
            deleteFeedResourcesUseCase(documentId)
            _viewState.value = FeedResourcesDetailViewState(isLoading = false)
        }
    }

}
