package com.example.hueverianieto.data.services

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetClientIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getClientId(): Long? = kotlin.runCatching {
        firebaseClient.db
            .collection("client_info")
            .get()
            .await()
    }.toIntResponse()

    private fun Result<QuerySnapshot>.toIntResponse() = when (val result = getOrNull()) {
        null -> null
        else -> (result.documents.size).toLong()
    }

}
