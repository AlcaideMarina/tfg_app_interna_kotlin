package com.example.hueverianieto.data.models.local

data class UserLoginData (
    val email: String = "",
    val password: String = "",
    val error: Boolean = false
)