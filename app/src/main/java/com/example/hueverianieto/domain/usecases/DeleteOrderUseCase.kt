package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteOrderService
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(
    private val deleteOrderService: DeleteOrderService
) {

    suspend operator fun invoke(clientDocumentId: String, orderDocumentId: String) =
        deleteOrderService.deleteOrder(clientDocumentId, orderDocumentId)

}
