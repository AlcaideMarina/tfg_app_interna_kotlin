package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.dailymonitoringcompanysituation

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
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.domain.usecases.GetDailyMonitoringCompanySituationUseCase
import com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation.MonthlyMonitoringCompanySituationViewModel
import com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.weeklymonitoringcompanysituation.WeeklyMonitoringCompanySituationViewState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyMonitoringCompanySituationViewModel @Inject constructor(
    val getDailyMonitoringCompanySituationUseCase: GetDailyMonitoringCompanySituationUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(DailyMonitoringCompanySituationViewState())
    val viewState: StateFlow<DailyMonitoringCompanySituationViewState> get() = _viewState

    private val _monitoringCompanySituationData = MutableLiveData<MonitoringCompanySituationData?>()
    val monitoringCompanySituationData: LiveData<MonitoringCompanySituationData?> get() = _monitoringCompanySituationData

    fun getDailyMonitoringCompanySituation(timestamp: Timestamp) {
        viewModelScope.launch {
            _viewState.value = DailyMonitoringCompanySituationViewState(isLoading = true)
            _monitoringCompanySituationData.value = getDailyMonitoringCompanySituationUseCase(timestamp)
            _viewState.value = DailyMonitoringCompanySituationViewState(isLoading = false)
        }
    }

    fun navigateToModifyDailyMonitoringCompanySituation(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_dailyMonitoringCompanySituationFragment_to_modifyDailyMonitoringCompanySituationFragment, bundle)
            ?: Log.e(
                DailyMonitoringCompanySituationViewModel::class.java.simpleName,
                "Error a la navegación de seguimiento de situación de la empresa - vista semanal"
            )
    }

}
