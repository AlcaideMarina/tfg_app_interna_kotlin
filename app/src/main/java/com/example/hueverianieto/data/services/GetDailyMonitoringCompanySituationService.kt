package com.example.hueverianieto.data.services

import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetDailyMonitoringCompanySituationService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getDailyMonitoringCompanySituation(timestamp: Timestamp): Result<QuerySnapshot> =
        runCatching {
            firebaseClient.db
                .collection("farm_situation")
                .whereGreaterThanOrEqualTo("situation_datetime", timestamp)
                .whereLessThan(
                    "situation_datetime",
                    Timestamp(Utils.addToDate(timestamp.toDate(), 1))
                )
                .get()
                .await()
        }

}
