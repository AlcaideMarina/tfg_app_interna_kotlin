package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonitoringCompanySituationData (
    var brokenEggs: Long,
    var createdBy: String,
    var creationDatetime: Timestamp,
    var documentId: String?,
    var hens: Map<String, Long>,
    var lEggs: Map<String, Long>,
    var mEggs: Map<String, Long>,
    var sEggs: Map<String, Long>,
    var situationDatetime: Timestamp,
    var xlEggs: Map<String, Long>
) : Parcelable