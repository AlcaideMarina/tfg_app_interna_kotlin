package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.CreateAuthUserService
import javax.inject.Inject

class CreateAuthUserUseCase @Inject constructor(
    private val createAuthUserService: CreateAuthUserService
) {

    suspend operator fun invoke(email: String, password: String) : String? =
        createAuthUserService.createAuthUser(email, password)

}
