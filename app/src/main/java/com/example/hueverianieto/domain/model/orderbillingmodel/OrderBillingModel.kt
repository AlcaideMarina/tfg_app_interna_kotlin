package com.example.hueverianieto.domain.model.orderbillingmodel

import com.google.firebase.Timestamp

data class OrderBillingModel (
    val orderId: Long?,
    val orderDatetime: Timestamp,
    val paymentMethod: Long,
    val totalPrice: Number?,
    val paid: Boolean
)