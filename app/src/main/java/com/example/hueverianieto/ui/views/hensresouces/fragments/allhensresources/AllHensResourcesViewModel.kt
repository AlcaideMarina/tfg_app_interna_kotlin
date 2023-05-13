package com.example.hueverianieto.ui.views.hensresouces.fragments.allhensresources

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetHensUseCase
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.workersresources.fragment.allworkersresources.AllWorkersResourcesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllHensResourcesViewModel @Inject constructor(
    val getHensUseCase: GetHensUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(AllHensResourcesViewState())
    val viewState: StateFlow<AllHensResourcesViewState> get() = _viewState

    private val _hensList = MutableLiveData<List<HensResourcesData?>?>()
    val hensList: LiveData<List<HensResourcesData?>?> get() = _hensList

    fun getHens() {
        viewModelScope.launch {
            _viewState.value = AllHensResourcesViewState(isLoading = true)
            when(val result: List<HensResourcesData?>? = getHensUseCase()) {
                null -> {
                    _viewState.value = AllHensResourcesViewState(isLoading = false, error = true)
                    _hensList.value = listOf()
                }
                listOf<HensResourcesData>() -> {
                    _viewState.value = AllHensResourcesViewState(isLoading = false, error = false)
                    _hensList.value = listOf()
                }
                else -> {
                    _viewState.value = AllHensResourcesViewState(isLoading = false, error = false)
                    _hensList.value = result.sortedBy { it?.expenseDatetime }.reversed()
                }
            }
        }
    }

    fun navigationToHensResourcesDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_allHensResourcesFragment_to_hensResourcesDetailFragment, bundle)
            ?: Log.e(
                AllHensResourcesViewModel::class.simpleName,
                "Error en la navegación a detalle de recursos (gallinas)"
            )
    }

}