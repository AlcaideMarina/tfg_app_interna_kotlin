package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewInternalUserService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun addNewInternalUser(internalUserData: InternalUserData) : Boolean = kotlin.runCatching {
        firebaseClient.db
            .collection("user_info")
            .add(
                InternalUserUtils.parcelableToMap(internalUserData)
            )
            .await()
    }.toBooleanResult()

    private fun Result<DocumentReference>.toBooleanResult() = when(val result = getOrNull()) {
        null -> false
        else -> true
    }

}