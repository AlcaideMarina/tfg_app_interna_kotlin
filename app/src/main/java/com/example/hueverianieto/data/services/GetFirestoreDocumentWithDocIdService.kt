package com.example.hueverianieto.data.services

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetFirestoreDocumentWithDocIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getDocumentWithDocId(
        collection: String,
        documentId: String
    ): Result<DocumentSnapshot> = runCatching {
        firebaseClient.db
            .collection(collection)
            .document(documentId)
            .get()
            .await()
    }

}
