package com.example.hueverianieto.domain.model.componentclientmodel

import android.view.View
import com.example.hueverianieto.base.BaseModel

data class ComponentClientBillingModel (
    var id: String,
    var company: String,
    var onClickListener: View.OnClickListener
) : BaseModel
