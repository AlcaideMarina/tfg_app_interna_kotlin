package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.services.NewInternalUserService
import javax.inject.Inject

class NewInternalUserUseCase @Inject constructor(
    private val newInternalUserService: NewInternalUserService
) {

    suspend operator fun invoke(internalUserData: InternalUserData) : Boolean =
        newInternalUserService.addNewInternalUser(internalUserData)

}