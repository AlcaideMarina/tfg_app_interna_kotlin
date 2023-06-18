package com.example.hueverianieto.data.services

import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewDocumentService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun newDocument(
        collection: String,
        dataMap: Map<String, Any?>
    ): Result<DocumentReference> = runCatching {
        firebaseClient.db
            .collection(collection)
            .add(dataMap)
            .await()
    }

}
