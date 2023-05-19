package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.data.services.GetDailyMonitoringCompanySituationService
import com.example.hueverianieto.utils.FarmUtils
import com.google.firebase.Timestamp
import javax.inject.Inject

class GetDailyMonitoringCompanySituationUseCase @Inject constructor(
    private val getDailyMonitoringCompanySituationService: GetDailyMonitoringCompanySituationService
) {

    suspend operator fun invoke(timestamp: Timestamp) : MonitoringCompanySituationData? =
        when(val result = getDailyMonitoringCompanySituationService.getDailyMonitoringCompanySituation(timestamp).getOrNull()) {
            null -> null
            else -> {
                if (!result.isEmpty && result.documents.isNotEmpty()) {
                    val doc = result.documents.first()
                    if (doc.data != null) {
                        FarmUtils.monitoringCompanySituationMapToParcelable(doc.data!!, doc.id)
                    }
                }
                null
            }
        }


}
