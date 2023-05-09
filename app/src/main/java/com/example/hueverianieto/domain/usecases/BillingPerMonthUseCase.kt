package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.BillingPerMonthService
import javax.inject.Inject

class BillingPerMonthUseCase  @Inject constructor(
    private val billingPerMonthService: BillingPerMonthService
) {

    suspend operator fun invoke(documentId: String) : List<OrderData?>? =
        billingPerMonthService.getOrdersPerMonth(documentId)

}