package com.example.hueverianieto.domain.model.finalproductcontrol

import com.example.hueverianieto.data.models.remote.FPCData
import com.google.firebase.Timestamp

data class MonthlyFPCContainerModel(
    val initDate: Timestamp,
    var endDate: Timestamp,
    var fpcDataList: List<FPCData>
)