package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetClientIdService
import javax.inject.Inject

class GetClientIdUseCase @Inject constructor(
    private val getClientIdService: GetClientIdService
) {

    suspend operator fun invoke(): Long? =
        getClientIdService.getClientId()

}
