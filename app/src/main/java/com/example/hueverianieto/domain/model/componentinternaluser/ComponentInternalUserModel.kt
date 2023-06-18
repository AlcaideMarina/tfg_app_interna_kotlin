package com.example.hueverianieto.domain.model.componentinternaluser

import android.view.View.OnClickListener
import com.example.hueverianieto.base.BaseModel

class ComponentInternalUserModel(
    var id: String,
    var name: String,
    var surname: String,
    var dni: String,
    var jobRole: String,
    var onClickListener: OnClickListener?
) : BaseModel
