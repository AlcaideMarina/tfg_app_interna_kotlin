package com.example.hueverianieto.data.components

import android.view.View

data class ComponentClientModel(
    var id: String,
    var company: String,
    var cif: String,
    // TODO: orders
    var onClickListener: View.OnClickListener
)