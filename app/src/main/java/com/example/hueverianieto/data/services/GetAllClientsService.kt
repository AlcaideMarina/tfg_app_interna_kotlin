package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.model.componentclient.ComponentClientModel
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllClientsService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getAllClientsService(deleted: Boolean) : MutableList<ClientData?>? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .whereEqualTo("deleted", deleted)
            .get()
            .await()
    }.toClientData()

    private fun Result<QuerySnapshot>.toClientData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<ClientData?>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    if (ClientUtils.checkErrorMap(data) == null) {
                        val clientData = ClientUtils.mapToParcelable(data, document.id)
                        list.add(clientData)
                    }
                }
            }
            list
        }
    }

}