package com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.domain.usecases.DeleteFeedResourcesUseCase
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedResourcesDetailViewModel @Inject constructor(
    val deleteFeedResourcesUseCase: DeleteFeedResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(FeedResourcesDetailViewState())
    val viewState: StateFlow<FeedResourcesDetailViewState> get() = _viewState

    fun deleteFeedResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = FeedResourcesDetailViewState(isLoading = true)
            deleteFeedResourcesUseCase(documentId)
            _viewState.value = FeedResourcesDetailViewState(isLoading = false)
        }
    }

}
