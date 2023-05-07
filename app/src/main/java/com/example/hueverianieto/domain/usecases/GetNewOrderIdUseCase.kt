package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetNewOrderIdService
import javax.inject.Inject

class GetNewOrderIdUseCase @Inject constructor(
    private val getNewOrderIdService: GetNewOrderIdService
) {

    suspend operator fun invoke(clientDocumentId : String) : Long? =
        getNewOrderIdService.getOrderId(clientDocumentId)

}
