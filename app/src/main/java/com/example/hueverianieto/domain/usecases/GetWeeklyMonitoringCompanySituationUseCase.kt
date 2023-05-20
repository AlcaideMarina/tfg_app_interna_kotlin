package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.local.WeeklyMonitoringCompanySituationData
import com.example.hueverianieto.data.services.GetWeeklyMonitoringCompanySituationService
import com.example.hueverianieto.utils.FarmUtils
import com.google.firebase.Timestamp
import javax.inject.Inject
import kotlin.math.roundToInt

class GetWeeklyMonitoringCompanySituationUseCase @Inject constructor(
    private val getWeeklyMonitoringCompanySituationService: GetWeeklyMonitoringCompanySituationService
) {

    suspend operator fun invoke(initTimestamp: Timestamp, endTimestamp: Timestamp) :
            WeeklyMonitoringCompanySituationData {
        val response = getWeeklyMonitoringCompanySituationService.getMonitoringComponentSituation(
            initTimestamp, endTimestamp
        ).getOrNull()
        var averageNumberHensWeek: Number = 0
        var totalNumberHensWeek: Long = 0
        var weeklyLaying: Long = 0
        var weeklyLayingRate: Double = 0.0

        if (response != null && response.documents.isNotEmpty()) {
            for (r in response.documents) {
                if (r.data != null) {
                    val mcsParcelable = FarmUtils.monitoringCompanySituationMapToParcelable(r.data!!, r.id)
                    totalNumberHensWeek += (mcsParcelable.hens["alive"] as Long)
                    weeklyLaying += (mcsParcelable.xlEggs["eggs"] as Long) + (mcsParcelable.lEggs["eggs"] as Long) +(mcsParcelable.mEggs["eggs"] as Long) +(mcsParcelable.sEggs["eggs"] as Long)
                }
            }
            averageNumberHensWeek = totalNumberHensWeek / response.documents.size
            averageNumberHensWeek = if (averageNumberHensWeek == (0).toLong()) 1 else averageNumberHensWeek
            weeklyLayingRate = weeklyLaying / response.documents.size / averageNumberHensWeek.toDouble()
        }

        return WeeklyMonitoringCompanySituationData(
            averageNumberHensWeek,
            weeklyLaying,
            (weeklyLayingRate * 100.0).roundToInt() / 100.0
        )

    }

}