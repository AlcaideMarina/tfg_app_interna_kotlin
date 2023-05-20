package com.example.hueverianieto.domain.model

import com.example.hueverianieto.base.BaseModel
import com.google.firebase.Timestamp

class ComponentStockModel (
    var infoDate: Timestamp,
    val add: Long,
    val subtract: Long,
    val total: Long,
) : BaseModel