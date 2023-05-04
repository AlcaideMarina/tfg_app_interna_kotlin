package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.services.GetClientDataService
import javax.inject.Inject

class GetClientDataUseCase @Inject constructor(
    private val getClientDataService : GetClientDataService
) {

    suspend operator fun invoke(documentId: String) : ClientData? =
        getClientDataService.getClientData(documentId)

}