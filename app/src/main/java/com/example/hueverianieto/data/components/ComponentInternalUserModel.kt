package com.example.hueverianieto.data.components

import android.view.View.OnClickListener
import com.example.hueverianieto.base.BaseModel

class ComponentInternalUserModel (
    var id: String,
    var nameSurname: String,
    var dni: String,
    var jobRole: String,
    var onClickListener: OnClickListener
) : BaseModel
