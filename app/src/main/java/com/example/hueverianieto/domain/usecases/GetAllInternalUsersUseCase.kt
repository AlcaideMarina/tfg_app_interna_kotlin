package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.services.GetAllInternalUsersService
import javax.inject.Inject

class GetAllInternalUsersUseCase @Inject constructor(
    private val getAllInternalUsersService: GetAllInternalUsersService
) {

    suspend operator fun invoke(deleted: Boolean): List<InternalUserData?>? =
        getAllInternalUsersService.getAllInternalUsers(deleted)

}
