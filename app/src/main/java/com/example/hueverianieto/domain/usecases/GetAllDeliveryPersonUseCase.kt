package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.services.GetAllDeliveryPersonService
import javax.inject.Inject

class GetAllDeliveryPersonUseCase @Inject constructor(
    private val getAllDeliveryPersonService: GetAllDeliveryPersonService
) {

    suspend operator fun invoke() : List<InternalUserData?>? =
        getAllDeliveryPersonService.getAllDeliveryPerson()

}