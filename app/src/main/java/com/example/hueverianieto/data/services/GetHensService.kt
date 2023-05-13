package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.utils.MaterialUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetHensService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getHens() : List<HensResourcesData?>? = runCatching {
        firebaseClient.db
            .collection("material_hens")
            .get()
            .await()
    }.toHensResourcesData()

    private fun Result<QuerySnapshot>.toHensResourcesData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<HensResourcesData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    val hensResourcesData = MaterialUtils.hensMapToParcelable(data, document.id)
                    list.add(hensResourcesData)
                }
            }
            list
        }
    }

}
