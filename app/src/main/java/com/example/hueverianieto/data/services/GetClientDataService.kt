package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetClientDataService @Inject constructor(
    val firebaseClient: FirebaseClient
) {

    suspend fun getClientData(documentId: String) : ClientData? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .get()
            .await()
    }.toClientData()

    private fun Result<DocumentSnapshot>.toClientData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            var clientData : ClientData? = null
            if (result.data != null) {
                val data = result.data!!
                if (ClientUtils.checkErrorMap(data) == null) {
                    clientData = ClientUtils.mapToParcelable(data, result.id)
                }
            }
            clientData
        }
    }

}