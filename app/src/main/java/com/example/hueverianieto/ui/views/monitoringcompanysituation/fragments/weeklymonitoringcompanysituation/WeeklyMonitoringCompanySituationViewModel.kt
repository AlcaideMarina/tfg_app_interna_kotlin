package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.weeklymonitoringcompanysituation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.WeeklyMonitoringCompanySituationData
import com.example.hueverianieto.domain.usecases.GetWeeklyMonitoringCompanySituationUseCase
import com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation.MonthlyMonitoringCompanySituationViewModel
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeeklyMonitoringCompanySituationViewModel @Inject constructor(
    val getWeeklyMonitoringCompanySituationUseCase: GetWeeklyMonitoringCompanySituationUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(WeeklyMonitoringCompanySituationViewState())
    val viewState: StateFlow<WeeklyMonitoringCompanySituationViewState> get() = _viewState

    private val _weeklyMonitoringCompanySituationData =
        MutableLiveData<WeeklyMonitoringCompanySituationData?>()
    val weeklyMonitoringCompanySituationData: LiveData<WeeklyMonitoringCompanySituationData?> get() = _weeklyMonitoringCompanySituationData

    fun getWeeklyMonitoringCompanySituation(initTimestamp: Timestamp, endTimestamp: Timestamp) {
        viewModelScope.launch {
            _viewState.value = WeeklyMonitoringCompanySituationViewState(isLoading = true)
            _weeklyMonitoringCompanySituationData.value =
                getWeeklyMonitoringCompanySituationUseCase(initTimestamp, endTimestamp)
            _viewState.value = WeeklyMonitoringCompanySituationViewState(isLoading = false)
        }
    }

    fun navigateToDailyMonitoringCompanySituation(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(
            R.id.action_weeklyMonitoringCompanySituationFragment_to_dailyMonitoringCompanySituationFragment,
            bundle
        )
            ?: Log.e(
                MonthlyMonitoringCompanySituationViewModel::class.java.simpleName,
                "Error a la navegación de seguimiento de situación de la empresa - vista semanal"
            )
    }

}
