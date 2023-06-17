package com.example.hueverianieto.data.models.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeeklyMonitoringCompanySituationData (
    var hensLosses: Long,
    var weeklyLaying: Long,
    var xlEggs: Long,
    var lEggs: Long,
    var mEggs: Long,
    var sEggs: Long
) : Parcelable