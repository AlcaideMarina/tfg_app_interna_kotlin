package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.GetOrderService
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val getOrderService: GetOrderService
) {

    suspend operator fun invoke(clientDocumentId: String, orderDocumentId: String) : OrderData? =
        getOrderService.getOrder(clientDocumentId, orderDocumentId)

}