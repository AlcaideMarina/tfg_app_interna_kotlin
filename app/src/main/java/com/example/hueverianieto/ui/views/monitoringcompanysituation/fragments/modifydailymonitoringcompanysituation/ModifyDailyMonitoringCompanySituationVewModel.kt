package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.modifydailymonitoringcompanysituation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.domain.usecases.NewMonitoringCompanySituationUseCase
import com.example.hueverianieto.domain.usecases.UpdateDailyMonitoringCompanySituationUseCase
import com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.dailymonitoringcompanysituation.DailyMonitoringCompanySituationViewState
import com.example.hueverianieto.utils.FarmUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyDailyMonitoringCompanySituationVewModel @Inject constructor(
    val updateDailyMonitoringCompanySituationUseCase: UpdateDailyMonitoringCompanySituationUseCase,
    val newMonitoringCompanySituationUseCase: NewMonitoringCompanySituationUseCase,
    val get
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyDailyMonitoringCompanySituationViewState())
    val viewState: StateFlow<ModifyDailyMonitoringCompanySituationViewState> get() = _viewState

    private val _monitoringCompanySituationData = MutableLiveData<MonitoringCompanySituationData?>()
    val monitoringCompanySituationData: LiveData<MonitoringCompanySituationData?> get() = _monitoringCompanySituationData

    fun updateDailyMonitoringCompanySituation(monitoringCompanySituationData: MonitoringCompanySituationData, documentId: String) {
        viewModelScope.launch {
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
            updateDailyMonitoringCompanySituationUseCase(
                FarmUtils.monitoringCompanySituationParcelableToMap(monitoringCompanySituationData),
                documentId
            )
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = false)
            // TODO: Falta el alertdialog
        }
    }

    fun addDailyMonitoringCompanySituation(monitoringCompanySituationData: MonitoringCompanySituationData) {
        viewModelScope.launch {
            // TODO: Antes de guardar, hay que ver las gallinas vivas que hay
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
            newMonitoringCompanySituationUseCase(
                monitoringCompanySituationData
            )
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
            // TODO: Falta el alertdialog
        }
    }


}
