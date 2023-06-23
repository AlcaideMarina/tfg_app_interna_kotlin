package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.FarmUtils
import javax.inject.Inject

class NewMonitoringCompanySituationUseCase @Inject constructor(
    val newDocumentService: NewDocumentService
) {

    suspend operator fun invoke(monitoringCompanySituationData: MonitoringCompanySituationData): Boolean {
        val dataMap =
            FarmUtils.monitoringCompanySituationParcelableToMap(monitoringCompanySituationData)
        return when (newDocumentService.newDocument("farm_situation", dataMap).getOrNull()) {
            null -> false
            else -> true
        }
    }

}
