package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.utils.MaterialUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetFeedService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getFeed() : List<FeedResourcesData?>? = runCatching {
        firebaseClient.db
            .collection("material_feed")
            .whereEqualTo("deleted", false)
            .get()
            .await()
    }.toFeedResourcesData()

    private fun Result<QuerySnapshot>.toFeedResourcesData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<FeedResourcesData>()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    val feedResourcesData = MaterialUtils.feedMapToParcelable(data, document.id)
                    list.add(feedResourcesData)
                }
            }
            list
        }
    }

}
