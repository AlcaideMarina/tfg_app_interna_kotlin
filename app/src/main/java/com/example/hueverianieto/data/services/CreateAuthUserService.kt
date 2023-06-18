package com.example.hueverianieto.data.services

import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreateAuthUserService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun createAuthUser(email: String, password: String): String? = kotlin.runCatching {
        firebaseClient.auth
            .createUserWithEmailAndPassword(email, password)
            .await()
    }.toStringResult()

    private fun Result<AuthResult>.toStringResult() = when (val result = getOrNull()) {
        null -> null
        else -> result.user?.uid
    }

}
