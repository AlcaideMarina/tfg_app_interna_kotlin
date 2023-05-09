package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetInternalUserWithIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getInternalUserWithId(documentId: String) : InternalUserData? = runCatching {
        firebaseClient.db
            .collection("user_info")
            .document(documentId)
            .get()
            .await()
    }.toInternalUserResponse()

    private fun Result<DocumentSnapshot>.toInternalUserResponse() = when(val result = getOrNull()) {
        null -> null
        else -> {
            if (result.data != null) {
                InternalUserUtils.mapToParcelable(result.data!!, result.id)
            } else null
        }
    }

}