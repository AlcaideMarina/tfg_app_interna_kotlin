package com.example.hueverianieto.domain.model.billingcontaineritemmodel

import android.view.View
import com.example.hueverianieto.domain.model.bilingpermonth.BillingPerMonthContainerModel

data class BillingContainerItemModel (
    var billingPerMonthContainerModel: BillingPerMonthContainerModel,
    var onClickListener: View.OnClickListener
)