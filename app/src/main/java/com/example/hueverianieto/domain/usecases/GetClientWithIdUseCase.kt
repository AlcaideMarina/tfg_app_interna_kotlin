package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetClientWithIdService
import javax.inject.Inject

class GetClientWithIdUseCase @Inject constructor(
    private val getClientWithIdService: GetClientWithIdService
) {

    suspend operator fun invoke(clientId: Long) =
        getClientWithIdService.getClientData(clientId)

}
