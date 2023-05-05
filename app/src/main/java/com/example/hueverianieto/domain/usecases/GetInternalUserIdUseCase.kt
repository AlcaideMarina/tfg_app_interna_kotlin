package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetInternalUserIdService
import javax.inject.Inject

class GetInternalUserIdUseCase @Inject constructor(
    private val getInternalUserIdService: GetInternalUserIdService
) {

    suspend operator fun invoke() : Long? = getInternalUserIdService.getInternalUserId()

}
