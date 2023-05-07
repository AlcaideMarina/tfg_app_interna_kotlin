package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetClientWithIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getClientData(id: Long) : ClientData? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .whereEqualTo("id", id)
            .get()
            .await()
    }.toClientDataResult()

    private fun Result<QuerySnapshot>.toClientDataResult() = when(val result = getOrNull()) {
        null -> null
        else -> {
            if (!result.isEmpty && result.documents.size > 0 && result.documents[0] != null
                && result.documents[0].data != null) {
                ClientUtils.mapToParcelable(result.documents[0].data!!, result.documents[0].id)
            } else null
        }
    }

}