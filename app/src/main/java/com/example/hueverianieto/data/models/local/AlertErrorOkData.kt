package com.example.hueverianieto.data.models.local

import android.view.View.OnClickListener

data class AlertErrorOkData (
    var title: String = "",
    var text: String = "",
    var finish: Boolean = false,
    var customCode: Int? = null
)
