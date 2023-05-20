package com.example.hueverianieto.domain.model.finalproductcontrol

import android.view.View
import com.example.hueverianieto.data.models.remote.FPCData

data class FPCDailyContainerItemModel (
    val fpcData: FPCData,
    var onClickListener: View.OnClickListener
)