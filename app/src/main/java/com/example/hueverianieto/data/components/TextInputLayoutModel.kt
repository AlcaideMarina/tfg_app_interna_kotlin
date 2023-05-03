package com.example.hueverianieto.data.components

import android.text.method.TransformationMethod
import com.example.hueverianieto.base.BaseModel

class TextInputLayoutModel(
    var inputText: String? = null,
    var inputType: Int? = null,
    var hintText: String? = null,
    var radius: Float? = null,
    var strokeColor: Int? = null,
    var iconMode: Int? = null,
    var transformationMethod: TransformationMethod? = null,
    var maxLines: Int? = null
) : BaseModel
