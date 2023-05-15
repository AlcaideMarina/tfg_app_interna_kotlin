package com.example.hueverianieto.data.services

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllDocumentsFromCollectionService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getDocumentsFromCollection(collection: String) : Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection(collection)
            .get()
            .await()
    }

}
