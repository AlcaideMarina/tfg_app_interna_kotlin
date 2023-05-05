package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteClientService
import javax.inject.Inject

class DeleteClientUseCase @Inject constructor(
    private val deleteClientService: DeleteClientService
) {

    suspend operator fun invoke(documentId: String) : Boolean =
        deleteClientService.deleteClient(documentId)

}