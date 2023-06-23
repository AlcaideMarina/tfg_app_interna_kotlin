package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.services.GetInternalUserWithIdService
import javax.inject.Inject

class GetInternalUserWithIdUseCase @Inject constructor(
    private val getInternalUserWithIdService: GetInternalUserWithIdService
) {

    suspend operator fun invoke(documentId: String): InternalUserData? =
        getInternalUserWithIdService.getInternalUserWithId(documentId)

}
