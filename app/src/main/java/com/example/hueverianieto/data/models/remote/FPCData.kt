package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
class FPCData (
    var acceptedEggs: Long,
    var bestBeforeDatetime: Timestamp,
    var createdBy: String,
    var creationDatetime: Timestamp,
    var deleted: Boolean,
    var issueDatetime: Timestamp,
    var layingDatetime: Timestamp,
    var lot: Long,
    var packingDatetime: Timestamp,
    var ejectedEggs: Long
) : Parcelable