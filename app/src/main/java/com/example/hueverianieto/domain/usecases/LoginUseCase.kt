package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.local.LoginResponse
import com.example.hueverianieto.data.services.AuthenticationService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend operator fun invoke(email: String, password: String): LoginResponse =
        authenticationService.login(email, password)

}
