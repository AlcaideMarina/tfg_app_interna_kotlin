package com.example.hueverianieto.data.models.local

import android.view.View
import com.google.firebase.Timestamp

data class OrderContainerModel(
    var orderDate: Timestamp,
    var orderId: Long,
    var company: String,
    var orderSummary: String = "...",
    var price: Number,
    var status: Long,
    var deliveryDni: String?,
    var onClickListener: View.OnClickListener
)
