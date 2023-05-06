package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class GetClientTodayOrdersService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getClientTodayOrders(documentId: String) : List<OrderData?>?  {
        val calendar = Calendar.getInstance()
        calendar.time = Timestamp.now().toDate()
        var d = (calendar.get(Calendar.DAY_OF_MONTH)).toString()
        while (d.length < 2) d = "0" + d
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        while (m.length < 2) m = "0" + m
        var y = calendar.get(Calendar.YEAR).toString()
        while (y.length < 4) y = "0" + y

        val today = Utils.parseStringToTimestamp(
            "$d/$m/$y 00:00:00", pattern = "dd/MM/yyyy hh:mm:ss"
        )
        val tomorrow = Timestamp(Utils.addToDate(today.toDate(), daysToAdd = -1))
        return runCatching {
            firebaseClient.db
                .collection("client_info")
                .document(documentId)
                .collection("orders")
                .whereGreaterThanOrEqualTo("order_datetime", today)
                .get()
                .await()
        }.toOrderDataList()
    }

    private fun Result<QuerySnapshot>.toOrderDataList() : List<OrderData?>? = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<OrderData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (item in result.documents) {
                    if (item.data != null) {
                        val data = item.data!!
                        data["order"]
                        list.add(OrderUtils.mapToParcelable(data))
                    }
                }
            }
            list
        }
    }


}
