package com.example.hueverianieto.data.services

import android.os.Parcelable
import com.example.hueverianieto.base.BaseModel
import com.example.hueverianieto.utils.DefaultConstantsUtils
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UpdateConstantService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun updateConstantService(map: Map<String, Any?>, constantName: String, userDocumentId: String) : Result<QuerySnapshot> = runCatching {
        firebaseClient.db
            .collection("default_constants")
            .whereEqualTo("constant_name", constantName)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val docs = task.result
                    if (docs != null && docs.documents.isNotEmpty()) {
                        val document = docs.documents[0]
                        firebaseClient.db
                            .collection("default_constants")
                            .document(document.id)
                            .set(
                                mapOf(
                                    "modified_by" to userDocumentId,
                                    "values" to map
                                ),
                                SetOptions.merge()
                            )
                    }
                }
            }
            .await()
    }

}