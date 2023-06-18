package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllDeliveryPersonService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getAllDeliveryPerson(): List<InternalUserData?>? = runCatching {
        firebaseClient.db
            .collection("user_info")
            .whereEqualTo("position", (2).toLong())
            .get()
            .await()
    }.toInternalUserDataList()

    private fun Result<QuerySnapshot>.toInternalUserDataList() = when (val result = getOrNull()) {
        null -> null
        else -> {
            val list: MutableList<InternalUserData> = mutableListOf()
            if (!result.isEmpty && result.documents.size > 0) {
                for (document in result) {
                    val data = document.data
                    if (InternalUserUtils.checkErrorMap(data) == null) {
                        val internalUserData = InternalUserUtils.mapToParcelable(data, document.id)
                        list.add(internalUserData)
                    }
                }
            }
            list
        }
    }

}
