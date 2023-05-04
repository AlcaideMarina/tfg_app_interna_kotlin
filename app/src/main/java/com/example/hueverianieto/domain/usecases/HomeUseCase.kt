package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.AuthenticationService
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {
    // TODO: Logout
}
