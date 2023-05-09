package com.example.hueverianieto.domain.model.bilingpermonth

import com.example.hueverianieto.domain.model.billingmodel.BillingModel
import com.google.firebase.Timestamp

class BillingPerMonthContainerModel  (
    var initDate: Timestamp,
    var endDate: Timestamp,
    //var onClickListener: OnClickListener?, // TODO: Esto va a ir en otro modelo nuevo -> item
    var billingModel: BillingModel?
)