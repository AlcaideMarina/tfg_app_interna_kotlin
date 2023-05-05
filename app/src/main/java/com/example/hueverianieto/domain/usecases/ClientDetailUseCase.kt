package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.AuthenticationService
import javax.inject.Inject

// TODO: Lo dejo así, porque, por ahora, no hay ningun servicio que utilizar
class ClientDetailUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

}
