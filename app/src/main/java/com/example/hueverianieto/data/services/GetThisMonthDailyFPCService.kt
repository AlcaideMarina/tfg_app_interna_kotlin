package com.example.hueverianieto.data.services

import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetThisMonthDailyFPCService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getThisMonthDailyFPC(
        initTimestamp: Timestamp,
        endTimestamp: Timestamp
    ): Result<QuerySnapshot> = kotlin.runCatching {
        firebaseClient.db
            .collection("final_product_control")
            .whereGreaterThanOrEqualTo("laying_datetime", initTimestamp)
            .whereLessThanOrEqualTo("laying_datetime", endTimestamp)
            .get()
            .await()
    }

}
