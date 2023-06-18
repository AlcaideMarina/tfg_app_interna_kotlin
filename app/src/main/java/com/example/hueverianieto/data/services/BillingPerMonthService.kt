package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.OrderUtils
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BillingPerMonthService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getOrdersPerMonth(documentId: String) = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .collection("orders")
            .orderBy("order_id", Query.Direction.DESCENDING)
            .get()
            .await()
    }.toOrderDataList()

    private fun Result<QuerySnapshot>.toOrderDataList(): List<OrderData?>? =
        when (val result = getOrNull()) {
            null -> null
            else -> {
                val list = mutableListOf<OrderData>()
                if (!result.isEmpty && result.documents.size > 0) {
                    for (item in result.documents) {
                        if (item.data != null) {
                            val data = item.data!!
                            data["order"]
                            list.add(OrderUtils.mapToParcelable(data, item.id))
                        }
                    }
                }
                list
            }
        }

}
