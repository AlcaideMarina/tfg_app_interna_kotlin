package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.services.NewClientService
import javax.inject.Inject

class NewClientUseCase @Inject constructor(
    private val newClientService: NewClientService
) {

    suspend operator fun invoke(clientData: ClientData) : Boolean =
        newClientService.addNewClient(clientData)

}