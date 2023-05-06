package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.OrderUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllClientOrdersService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getAllClientOrders(documentId: String) : List<OrderData?>? =  runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .collection("orders")
            .get()
            .await()
    }.toOrderDataList()

    private fun Result<QuerySnapshot>.toOrderDataList() : List<OrderData?>? = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<OrderData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (item in result.documents) {
                    if (item.data != null) {
                        val data = item.data!!
                        list.add(OrderUtils.mapToParcelable(data, item.id))
                    }
                }
            }
            list
        }
    }

}