package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserDataService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun getUserData(uid: String) : InternalUserData? = runCatching {
        firebaseClient.db
            .collection("client_info")
            .whereEqualTo("uid", uid)
            .get()
            .await()
    }.toInternalUserData()

    private fun Result<QuerySnapshot>.toInternalUserData() = when(val result = getOrNull()) {
        null -> null
        else -> {
            if (!result.isEmpty && result.documents.size > 0 && result.documents[0] != null
                && result.documents[0].data != null) {
                InternalUserUtils.mapToParcelable(result.documents[0].data!!, result.documents[0].id)
            } else null
        }
    }

}
