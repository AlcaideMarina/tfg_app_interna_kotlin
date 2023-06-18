package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.UpdateOrderService
import javax.inject.Inject

class UpdateOrderUseCase @Inject constructor(
    private val updateOrderService: UpdateOrderService
) {

    suspend operator fun invoke(clientDocumentId: String, orderData: OrderData): Boolean =
        updateOrderService.updateOrder(clientDocumentId, orderData)

}
