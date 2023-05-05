package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetAllInternalUsersService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getAllInternalUsers(deleted: Boolean) : MutableList<InternalUserData?>? = runCatching {
        firebaseClient.db
            .collection("user_info")
            .whereEqualTo("deleted", deleted)
            .get()
            .await()
    }.toInternalUserData()

    private fun Result<QuerySnapshot>.toInternalUserData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            val list = mutableListOf<InternalUserData?>()
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