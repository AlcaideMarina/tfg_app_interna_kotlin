package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.monthlyfinalproductcontrol

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.domain.usecases.GetMonthlyFPCUseCase
import com.example.hueverianieto.domain.usecases.GetNextLotUseCase
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol.DailyFinalProductControlViewModel
import com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers.AllInternalUsersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyFinalProductControlViewModel @Inject constructor(
    val getMonthlyFPCUseCase: GetMonthlyFPCUseCase,
    val getNextLotUseCase: GetNextLotUseCase
) : ViewModel() {

    private var _viewState = MutableStateFlow(MonthlyFinalProductControlViewState())
    val viewState: StateFlow<MonthlyFinalProductControlViewState> get() = _viewState

    private val _monthlyFPCContainerModelList = MutableLiveData<List<MonthlyFPCContainerModel?>>()
    val monthlyFPCContainerModelList: LiveData<List<MonthlyFPCContainerModel?>> get() = _monthlyFPCContainerModelList

    private val _lot = MutableLiveData<Int>()
    val lot: LiveData<Int> get() = _lot

    fun getMonthlyFPCData() {
        viewModelScope.launch {
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = true)
            val result = getMonthlyFPCUseCase()
            _monthlyFPCContainerModelList.value = result
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = false)

        }
    }

    fun getNextLot() {
        viewModelScope.launch {
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = true)
            val result = getNextLotUseCase()
            _lot.value = result
            _viewState.value = MonthlyFinalProductControlViewState(isLoading = false)
        }
    }

    fun navigateToDalyFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_monthlyFinalProductControlFragment_to_dailyFinalProductControlFragment, bundle)
            ?: Log.e(
                AllInternalUsersViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

    fun navigateToNewFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_monthlyFinalProductControlFragment_to_newFinalProductControlFragment, bundle)
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }


}
