package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.GetAllClientOrdersService
import javax.inject.Inject

class GetAllClientOrdersUseCase @Inject constructor(
    private val getAllClientOrdersService: GetAllClientOrdersService
) {

    suspend operator fun invoke(documentId: String): List<OrderData?>? =
        getAllClientOrdersService.getAllClientOrders(documentId)

}