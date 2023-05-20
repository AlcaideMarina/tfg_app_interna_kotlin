package com.example.hueverianieto.domain.model.finalproductcontrol

import android.os.Parcelable
import com.example.hueverianieto.data.models.remote.FPCData
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class MonthlyFPCContainerModel(
    val initDate: Timestamp,
    var endDate: Timestamp,
    var fpcDataList: List<FPCData>
) : Parcelable
