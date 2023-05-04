package com.example.hueverianieto.domain.model.textinputlayout

import com.example.hueverianieto.base.BaseModel

class RowTextInputLayoutModel(
    var leftTextInputLayoutModel: TextInputLayoutModel = TextInputLayoutModel(),
    var rightTextInputLayoutModel: TextInputLayoutModel? = null
) : BaseModel
