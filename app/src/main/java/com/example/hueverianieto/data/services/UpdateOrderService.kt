package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.OrderUtils
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateOrderService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun updateOrder(clientDocumentId : String, orderData: OrderData) : Boolean = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(clientDocumentId)
            .collection("orders")
            .document(orderData.documentId!!)
            .set(
                OrderUtils.parcelableToMap(orderData),
                SetOptions.merge()
            )
            .await()
    }.toBooleanResult()

    private fun Result<Void>.toBooleanResult() = when (val result = getOrNull()) {
        null -> true
        else -> true
    }
}