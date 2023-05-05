package com.example.hueverianieto.data.services

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetInternalUserIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getInternalUserId() : Long? = kotlin.runCatching {
        firebaseClient.db
            .collection("user_info")
            .get()
            .await()
    }.toIntResponse()

    private fun Result<QuerySnapshot>.toIntResponse() = when(val result = getOrNull()) {
        null -> null
        else -> (result.documents.size).toLong()
    }

}