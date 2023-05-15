package com.example.hueverianieto.ui.views.finalproductcontrol.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.domain.usecases.GetMonthlyFPCUseCase
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.clientsbilling.fragments.billingpermonth.BillingPerMonthViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyFinalProductControlViewModel @Inject constructor(
    val getMonthlyFPCUseCase: GetMonthlyFPCUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow(MonthlyFinalProductControlViewState())
    val viewState: StateFlow<MonthlyFinalProductControlViewState> get() = _viewState

    private val _monthlyFPCContainerModelList = MutableLiveData<List<MonthlyFPCContainerModel?>>()
    val monthlyFPCContainerModelList: LiveData<List<MonthlyFPCContainerModel?>> get() = _monthlyFPCContainerModelList

    fun getMonthlyFPCData() {
        viewModelScope.launch {
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = true)
            val result = getMonthlyFPCUseCase()
            _monthlyFPCContainerModelList.value = result
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = false)

        }
    }

}
