package com.example.hueverianieto.data.services

import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteDocumentWithDocIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun deleteDocument(collection: String, documentId: String) {
        val deleted: Map<String, Boolean> = mapOf("deleted" to true)
        runCatching {
            firebaseClient.db
                .collection(collection)
                .document(documentId)
                .set(
                    deleted,
                    SetOptions.merge()
                )
                .await()
        }
    }

}
