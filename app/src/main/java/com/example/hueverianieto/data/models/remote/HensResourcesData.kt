package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class HensResourcesData(
    var createdBy: String,
    var creationDatetime: Timestamp,
    var deleted: Boolean,
    var documentId: String?,
    var expenseDatetime: Timestamp,
    var hensNumber: Long,
    var shedA: Long,
    var shedB: Long,
    var totalPrice: Number
) : Parcelable
