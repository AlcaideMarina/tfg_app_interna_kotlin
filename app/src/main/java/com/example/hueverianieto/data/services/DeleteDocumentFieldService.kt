package com.example.hueverianieto.data.services

import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteDocumentFieldService @Inject constructor(
    private var firebaseClient: FirebaseClient
) {

    suspend fun deleteDocumentField(documentId: String, collection: String) : Boolean {
        val deleted : Map<String, Boolean> = mapOf("deleted" to true)
        return runCatching {
            firebaseClient.db
                .collection("client_info")
                .document(documentId)
                .set(deleted,
                    SetOptions.merge()
                )
                .await()
        }.toBooleanResult()
    }


    private fun Result<Void>.toBooleanResult() = when (val result = getOrNull()) {
        null -> true
        else -> true
    }
}