package com.example.hueverianieto.data.services

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllDocumentsIdService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getAllDocumentsId(collection: String) : List<String?>? = kotlin.runCatching {
        firebaseClient.db
            .collection(collection)
            .get()
            .await()
    }.toIdList()

    private fun Result<QuerySnapshot>.toIdList() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list : MutableList<String> = mutableListOf()
            if (!result.isEmpty && result.documents.size > 0) {
                for (item in result.documents) {
                    list.add(item.id)
                }
            }
            list
        }
    }

}