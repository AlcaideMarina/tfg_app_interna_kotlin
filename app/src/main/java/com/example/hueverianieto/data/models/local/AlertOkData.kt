package com.example.hueverianieto.data.models.local

data class AlertOkData(
    var title: String = "",
    var text: String = "",
    var finish: Boolean = false,
    var customCode: Int? = null
)
