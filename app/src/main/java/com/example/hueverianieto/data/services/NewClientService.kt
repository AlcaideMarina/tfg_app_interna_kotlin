package com.example.hueverianieto.data.services

import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NewClientService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

    suspend fun addNewClient(clientData: ClientData) : Boolean = kotlin.runCatching {
        firebaseClient.db
            .collection("client_info")
            .add(
                ClientUtils.parcelableToMap(clientData)
            )
            .await()
    }.toBooleanResult()

    private fun Result<DocumentReference>.toBooleanResult() = when(val result = getOrNull()) {
        null -> false
        else -> true
    }

}