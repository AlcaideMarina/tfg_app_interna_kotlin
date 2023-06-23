package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.GetClientTodayDeliveriesService
import javax.inject.Inject

class GetClientTodayDeliveriesUseCase @Inject constructor(
    private val getClientTodayDeliveriesService: GetClientTodayDeliveriesService
) {

    suspend operator fun invoke(documentId: String): List<OrderData?>? =
        getClientTodayDeliveriesService.getClientTodayDeliveries(documentId)

}
