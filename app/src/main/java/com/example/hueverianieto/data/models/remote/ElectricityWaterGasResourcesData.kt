package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
class ElectricityWaterGasResourcesData (
    var createBy: String,
    var creationDatatime: Timestamp,
    var deleted: Boolean,
    var documentId: String?,
    var expenseDatetime: Timestamp,
    var notes: String,
    var totalPrice: Number,
    var type: Long
) : Parcelable