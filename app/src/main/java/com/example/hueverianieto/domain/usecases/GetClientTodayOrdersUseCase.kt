package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.data.services.GetClientTodayOrdersService
import javax.inject.Inject

class GetClientTodayOrdersUseCase @Inject constructor(
    private val getClientTodayOrdersService: GetClientTodayOrdersService
) {

    suspend operator fun invoke(documentId : String) :List<OrderData?>? =
        getClientTodayOrdersService.getClientTodayOrders(documentId)

}