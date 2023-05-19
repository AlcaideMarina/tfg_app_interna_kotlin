package com.example.hueverianieto.data.services

import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetDailyMonitoringCompanySituationService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getDailyMonitoringCompanySituation(timestamp: Timestamp) : Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection("farm_situation")
            .whereEqualTo("situation_datetime", timestamp)
            .get()
            .await()
    }

}
