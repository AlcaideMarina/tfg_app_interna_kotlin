package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.boxesandcartonsresourcesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.domain.usecases.DeleteBoxesAndCartonsResourcesUseCase
import com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail.FeedResourcesDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoxesAndCartonsResourcesDetailViewModel @Inject constructor(
    val deleteBoxesAndCartonsResourcesUseCase: DeleteBoxesAndCartonsResourcesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(BoxesAndCartonsResourcesDetailViewState())
    val viewState: StateFlow<BoxesAndCartonsResourcesDetailViewState> get() = _viewState

    private val _feedResource = MutableLiveData<BoxesAndCartonsResourcesData?>()
    val feedResource: LiveData<BoxesAndCartonsResourcesData?> get() = _feedResource

    fun deleteBoxesAndCartonsResources(documentId: String) {
        viewModelScope.launch {
            _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = true)
            deleteBoxesAndCartonsResourcesUseCase(documentId)
            _viewState.value = BoxesAndCartonsResourcesDetailViewState(isLoading = false)
        }
    }

}
