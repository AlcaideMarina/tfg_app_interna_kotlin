package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.modifydailymonitoringcompanysituation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.domain.usecases.GetHensUseCase
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
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyDailyMonitoringCompanySituationViewState())
    val viewState: StateFlow<ModifyDailyMonitoringCompanySituationViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog : LiveData<AlertOkData> get() = _alertDialog

    fun updateDailyMonitoringCompanySituation(monitoringCompanySituationData: MonitoringCompanySituationData, documentId: String) {
        viewModelScope.launch {
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
            when (updateDailyMonitoringCompanySituationUseCase(
                FarmUtils.monitoringCompanySituationParcelableToMap(monitoringCompanySituationData),
                documentId
            )) {
                false -> {
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar la situación de la empresa diaria. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                    _viewState.value =
                        ModifyDailyMonitoringCompanySituationViewState(isLoading = false)
                }
                else -> {
                    _alertDialog.value = AlertOkData(
                        title = "Situación guardada",
                        text = "La información sobre la situación de la empresa diaría ha sido guardada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
        }
    }

    fun addDailyMonitoringCompanySituation(monitoringCompanySituationData: MonitoringCompanySituationData) {
        viewModelScope.launch {
            // TODO: Antes de guardar, hay que ver las gallinas vivas que hay
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
            when (newMonitoringCompanySituationUseCase(
                monitoringCompanySituationData
            )) {
                false -> {
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error al guardar la situación de la empresa diaria. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                    _viewState.value =
                        ModifyDailyMonitoringCompanySituationViewState(isLoading = false)
                }
                else -> {
                    _alertDialog.value = AlertOkData(
                        title = "Situación guardada",
                        text = "Las información sobre la situación de la empresa diaría ha sido guardada correctamente en la base de datos.",
                        true,
                        customCode = 0
                    )
                }
            }
            _viewState.value = ModifyDailyMonitoringCompanySituationViewState(isLoading = true)
        }
    }


}
