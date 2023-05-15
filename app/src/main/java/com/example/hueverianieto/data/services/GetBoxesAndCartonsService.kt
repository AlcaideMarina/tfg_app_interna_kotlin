package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.utils.MaterialUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetBoxesAndCartonsService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getBoxesAndCartons() : List<BoxesAndCartonsResourcesData?>? = runCatching {
        firebaseClient.db
            .collection("material_feed")
            .whereEqualTo("deleted", false)
            .get()
            .await()
    }.toBoxesAndCartonsResourcesData()

    private fun Result<QuerySnapshot>.toBoxesAndCartonsResourcesData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<BoxesAndCartonsResourcesData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    val bcResourcesData = MaterialUtils.boxesAndCartonsMapToParcelable(data, document.id)
                    list.add(bcResourcesData)
                }
            }
            list
        }
    }

}
