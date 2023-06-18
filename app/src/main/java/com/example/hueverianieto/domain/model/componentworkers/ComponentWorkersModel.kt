package com.example.hueverianieto.domain.model.componentworkers

import android.view.View
import com.example.hueverianieto.base.BaseModel

class ComponentWorkersModel(
    var id: String,
    var name: String,
    var surname: String,
    var salary: Number?,
    var onClickListener: View.OnClickListener?
) : BaseModel
