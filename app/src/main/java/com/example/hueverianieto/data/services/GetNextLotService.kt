package com.example.hueverianieto.data.services

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetNextLotService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getNextLot(): Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection("final_product_control")
            .orderBy("lot", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
    }

}
