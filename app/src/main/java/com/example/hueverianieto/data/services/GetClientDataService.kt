package com.example.hueverianieto.data.services

import android.os.Parcelable
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GetClientDataService @Inject constructor(
    val firebaseClient: FirebaseClient
) {

    suspend fun getClientData(documentId: String, collection: String): Parcelable? = runCatching {
        firebaseClient.db
            .collection(collection)
            .document(documentId)
            .get()
            .await()
    }.toParcelable(collection)

    private fun Result<DocumentSnapshot>.toParcelable(collection: String) =
        when (val result = getOrNull()) {
            null -> null
            else -> {
                when (collection) {
                    "client_info" -> {
                        var clientData: ClientData? = null
                        if (result.data != null) {
                            val data = result.data!!
                            if (ClientUtils.checkErrorMap(data) == null) {
                                clientData = ClientUtils.mapToParcelable(data, result.id)
                            }
                        }
                        clientData
                    }
                    "user_info" -> {
                        var internalUserData: InternalUserData? = null
                        if (result.data != null) {
                            val data = result.data!!
                            if (InternalUserUtils.checkErrorMap(data) == null) {
                                internalUserData =
                                    InternalUserUtils.mapToParcelable(data, result.id)
                            }
                        }
                        internalUserData
                    }
                    else -> null
                }

            }
        }

}
