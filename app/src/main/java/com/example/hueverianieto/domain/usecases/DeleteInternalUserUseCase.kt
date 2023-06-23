package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteInternalUserService
import javax.inject.Inject

class DeleteInternalUserUseCase @Inject constructor(
    private val deleteInternalUserService: DeleteInternalUserService
) {

    suspend operator fun invoke(documentId: String): Boolean =
        deleteInternalUserService.deleteInternalUser(documentId)

}
