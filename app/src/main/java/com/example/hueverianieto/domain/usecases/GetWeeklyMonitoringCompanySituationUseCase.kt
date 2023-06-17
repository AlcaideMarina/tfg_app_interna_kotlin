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
        var hensLosses: Long = 0
        var weeklyLaying: Long = 0
        var xlEggs: Long = 0
        var lEggs: Long = 0
        var mEggs: Long = 0
        var sEggs: Long = 0

        if (response != null && response.documents.isNotEmpty()) {
            for (r in response.documents) {
                if (r.data != null) {
                    val mcsParcelable = FarmUtils.monitoringCompanySituationMapToParcelable(r.data!!, r.id)
                    hensLosses += mcsParcelable.hens["losses"] as Long
                    xlEggs += mcsParcelable.xlEggs["eggs"] as Long
                    lEggs += mcsParcelable.lEggs["eggs"] as Long
                    mEggs += mcsParcelable.mEggs["eggs"] as Long
                    sEggs += mcsParcelable.sEggs["eggs"] as Long
                    weeklyLaying += (mcsParcelable.xlEggs["eggs"] as Long) + (mcsParcelable.lEggs["eggs"] as Long) +(mcsParcelable.mEggs["eggs"] as Long) +(mcsParcelable.sEggs["eggs"] as Long)
                }
            }
        }

        return WeeklyMonitoringCompanySituationData(
            hensLosses,
            weeklyLaying,
            xlEggs,
            lEggs,
            mEggs,
            sEggs
        )

    }

}