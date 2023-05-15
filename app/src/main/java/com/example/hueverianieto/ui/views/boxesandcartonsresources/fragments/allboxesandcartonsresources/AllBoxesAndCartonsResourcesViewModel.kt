package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.allboxesandcartonsresources

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.domain.usecases.GetBoxesAndCartonsUseCase
import com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces.AllFeedResourcesViewModel
import com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces.AllFeedResourcesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBoxesAndCartonsResourcesViewModel @Inject constructor(
    val getBoxesAndCartonsUseCase: GetBoxesAndCartonsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllBoxesAndCartonsResourcesViewState())
    val viewState: StateFlow<AllBoxesAndCartonsResourcesViewState> get() = _viewState

    private val _cbList = MutableLiveData<List<BoxesAndCartonsResourcesData?>?>()
    val cbList: LiveData<List<BoxesAndCartonsResourcesData?>?> get() = _cbList

    fun getCB() {
        viewModelScope.launch {
            _viewState.value = AllBoxesAndCartonsResourcesViewState(isLoading = true)
            when(val result: List<BoxesAndCartonsResourcesData?>? = getBoxesAndCartonsUseCase()) {
                null -> {
                    _viewState.value = AllBoxesAndCartonsResourcesViewState(isLoading = false, error = true)
                    _cbList.value = listOf()
                }
                listOf<HensResourcesData>() -> {
                    _viewState.value = AllBoxesAndCartonsResourcesViewState(isLoading = false, error = false)
                    _cbList.value = listOf()
                }
                else -> {
                    _viewState.value = AllBoxesAndCartonsResourcesViewState(isLoading = false, error = false)
                    _cbList.value = result.sortedBy { it?.expenseDatetime }.reversed()
                }
            }
        }
    }

    fun navigationToBCResourcesDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_allBoxesAndCartonsResourcesFragment_to_boxesAndCartonsResourcesDetailFragment, bundle)
            ?: Log.e(
                AllFeedResourcesViewModel::class.simpleName,
                "Error en la navegación a detalle de recursos (pienso)"
            )
    }

}
