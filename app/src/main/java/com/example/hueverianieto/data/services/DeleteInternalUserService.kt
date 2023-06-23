package com.example.hueverianieto.data.services

import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteInternalUserService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun deleteInternalUser(documentId: String): Boolean {
        val deleted: Map<String, Boolean> = mapOf("deleted" to true)
        return runCatching {
            firebaseClient.db
                .collection("user_info")
                .document(documentId)
                .set(
                    deleted,
                    SetOptions.merge()
                )
                .await()
        }.toBooleanResult()
    }

    private fun Result<Void>.toBooleanResult() = true

}
