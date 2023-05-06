package com.example.hueverianieto.data.services

import com.example.hueverianieto.R
import com.example.hueverianieto.utils.Constants
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteOrderService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun deleteOrder(clientDocumentId: String, orderDocumentId: String) : Boolean {
        val deleted : Map<String, Any> = mapOf(
            "status" to Constants.orderStatus[R.string.cancelled]!!.toLong()
        )
        return runCatching {
            firebaseClient.db
                .collection("client_info")
                .document(clientDocumentId)
                .collection("orders")
                .document(orderDocumentId)
                .set(deleted,
                    SetOptions.merge()
                )
                .await()
        }.toBooleanResult()
    }


    private fun Result<Void>.toBooleanResult() = when (val result = getOrNull()) {
        null -> true
        else -> true
    }

}