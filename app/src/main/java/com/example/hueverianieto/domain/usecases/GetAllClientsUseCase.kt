package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.services.GetAllClientsService
import javax.inject.Inject

class GetAllClientsUseCase @Inject constructor(
    private val getAllClientsService: GetAllClientsService
) {

    suspend operator fun invoke(deleted: Boolean): List<ClientData?>? =
        getAllClientsService.getAllClientsService(deleted)

}
