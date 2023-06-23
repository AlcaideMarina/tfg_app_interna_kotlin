package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.domain.usecases.GetNextLotUseCase
import com.example.hueverianieto.domain.usecases.GetThisMonthDailyFPCUseCase
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyFinalProductControlViewModel @Inject constructor(
    var getThisMonthDailyFPCUseCase: GetThisMonthDailyFPCUseCase,
    val getNextLotUseCase: GetNextLotUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow(DailyFinalProductControlViewState())
    val viewState: StateFlow<DailyFinalProductControlViewState> get() = _viewState

    private val _monthlyFPCContainerModel = MutableLiveData<MonthlyFPCContainerModel?>()
    val monthlyFPCContainerModel: LiveData<MonthlyFPCContainerModel?> get() = _monthlyFPCContainerModel

    private val _lot = MutableLiveData<Int>()
    val lot: LiveData<Int> get() = _lot

    fun getThisMonthDailyFPC(initTimestamp: Timestamp, endTimestamp: Timestamp) {
        viewModelScope.launch {
            _viewState.value = DailyFinalProductControlViewState(isLoading = true)
            val result = getThisMonthDailyFPCUseCase(initTimestamp, endTimestamp)
            _monthlyFPCContainerModel.value = result
            _viewState.value = DailyFinalProductControlViewState(isLoading = false)
        }
    }

    fun getNextLot() {
        viewModelScope.launch {
            _viewState.value = DailyFinalProductControlViewState(isLoading = true)
            val result = getNextLotUseCase()
            _lot.value = result
            _viewState.value = DailyFinalProductControlViewState(isLoading = false)
        }
    }

    fun navigateToDailyDeletedFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_dailyFinalProductControlFragment_to_dailyDeletedFinalProductControlFragment,
            bundle
        )
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

    fun navigateToNewFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_dailyFinalProductControlFragment_to_newFinalProductControlFragment,
            bundle
        )
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

    fun navigateToFPCDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_dailyFinalProductControlFragment_to_finalProductControlDetailFragment,
            bundle
        )
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

}
