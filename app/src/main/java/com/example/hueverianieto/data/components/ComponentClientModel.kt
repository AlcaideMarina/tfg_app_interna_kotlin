package com.example.hueverianieto.data.components

import android.view.View
import com.example.hueverianieto.base.BaseModel

data class ComponentClientModel(
    var id: String,
    var company: String,
    var cif: String,
    // TODO: orders
    var onClickListener: View.OnClickListener
) : BaseModel
