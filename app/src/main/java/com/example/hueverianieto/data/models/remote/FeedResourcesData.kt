package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
class FeedResourcesData (
    var createdBy: String,
    var creationDatetime: Timestamp,
    var deleted: Boolean,
    var documentId: String?,
    var expenseDatetime: Timestamp,
    var kilos: Number,
    var totalPrice: Number
) : Parcelable