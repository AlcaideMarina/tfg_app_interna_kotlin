package com.example.hueverianieto.data.services

import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateFirestoreService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun updateDocumentFirestore(
        dataMap: Map<String, Any?>,
        documentId: String,
        collection: String
    ): Boolean = runCatching {
        firebaseClient.db
            .collection(collection)
            .document(documentId)
            .set(dataMap)
            .await()
    }.toBooleanResult()

    private fun Result<Void>.toBooleanResult() = when (val result = getOrNull()) {
        null -> true
        else -> true
    }

}
