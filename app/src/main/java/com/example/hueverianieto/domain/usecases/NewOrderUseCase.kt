package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.NewOrderService
import javax.inject.Inject

class NewOrderUseCase @Inject constructor(
    private val newOrderService: NewOrderService
) {

    suspend operator fun invoke(clientDocumentId: String, orderData: OrderData): Boolean =
        newOrderService.addNewOrder(clientDocumentId, orderData)

}
