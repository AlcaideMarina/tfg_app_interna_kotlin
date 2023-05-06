package com.example.hueverianieto.data.models.local

class GridTextItemData (
    val id: Int,
    val isTextView: Boolean,
    val text: String?,
    var isEnabled: Boolean = true,
    val isTextLeft: Boolean = true,
    var response: Any? = null
)