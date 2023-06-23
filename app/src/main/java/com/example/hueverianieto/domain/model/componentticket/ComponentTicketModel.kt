package com.example.hueverianieto.domain.model.componentticket

import android.view.View.OnClickListener
import com.example.hueverianieto.base.BaseModel
import com.google.firebase.Timestamp

class ComponentTicketModel(
    var expenseDate: Timestamp,
    var quantity: String,
    var units: String?,
    var price: Number,
    var onClickListener: OnClickListener
) : BaseModel
