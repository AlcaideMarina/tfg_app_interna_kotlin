package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.OrderUtils
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewOrderService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun addNewOrder(clientDocumentId: String, orderData: OrderData): Boolean = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(clientDocumentId)
            .collection("orders")
            .add(
                OrderUtils.parcelableToMap(orderData)
            ).await()
    }.toOrderResult()

    private fun Result<DocumentReference>.toOrderResult() = when (val result = getOrNull()) {
        null -> false
        else -> true
    }

}
