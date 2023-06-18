package com.example.hueverianieto.data.services

import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetWeeklyMonitoringCompanySituationService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getMonitoringComponentSituation(initTimestamp: Timestamp, endTimestamp: Timestamp):
            Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection("farm_situation")
            .whereGreaterThanOrEqualTo("situation_datetime", initTimestamp)
            .whereLessThanOrEqualTo("situation_datetime", endTimestamp)
            .get()
            .await()
    }

}
