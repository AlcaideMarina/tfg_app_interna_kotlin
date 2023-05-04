package com.example.hueverianieto.domain.model.containerbordercheck

import com.example.hueverianieto.base.BaseModel

data class ContainerBorderCheckTitleModel (
    var title: String,
    var body: List<BaseModel>
) : BaseModel
