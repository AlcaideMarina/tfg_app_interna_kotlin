package com.example.hueverianieto.data.services

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateFirestoreService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun updateClientFirestore(clientDataMap: Map<String, Any?>, documentId: String) : Boolean = runCatching {
        firebaseClient.db
            .collection("client_info")
            .document(documentId)
            .set(clientDataMap, SetOptions.merge())
            .await()
    }.toBooleanResult()

    private fun Result<Void>.toBooleanResult() = when (val result = getOrNull()) {
        null -> true
        else -> true
    }

}