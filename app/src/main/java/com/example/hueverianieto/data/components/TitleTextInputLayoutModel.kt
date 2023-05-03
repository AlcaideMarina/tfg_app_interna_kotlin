package com.example.hueverianieto.data.components

import com.example.hueverianieto.base.BaseModel

class TitleTextInputLayoutModel(
    val title: String,
    val rowTextInputLayoutModelList: List<RowTextInputLayoutModel> = listOf(RowTextInputLayoutModel())
) : BaseModel
