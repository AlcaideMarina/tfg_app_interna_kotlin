package com.example.hueverianieto.data.models.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeeklyMonitoringCompanySituationData (
    var averageNumberHensWeek: Number,
    var weeklyLaying: Long,
    var weeklyLayingRate: Number,
) : Parcelable