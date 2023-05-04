package com.example.hueverianieto.data.models.local

import com.example.hueverianieto.data.models.remote.InternalUserData

sealed class LoginResponse {
    object Error : LoginResponse()
    data class Success(val uid: String, val clientData: InternalUserData?) : LoginResponse()
}
