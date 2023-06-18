package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.OrderUtils
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetOrderService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getOrder(clientDocumentId: String, orderDocumentId: String): OrderData? =
        runCatching {
            firebaseClient.db
                .collection("client_info")
                .document(clientDocumentId)
                .collection("orders")
                .document(orderDocumentId)
                .get()
                .await()
        }.toOrderDataResponse()

    private fun Result<DocumentSnapshot>.toOrderDataResponse() = when (val result = getOrNull()) {
        null -> null
        else -> {
            if (result.data != null) {
                OrderUtils.mapToParcelable(result.data!!, result.id)
            } else null
        }
    }

}
