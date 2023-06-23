package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateFirestoreUserUseCase @Inject constructor(
    private var updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(
        clientDataMap: Map<String, Any?>,
        documentId: String,
        collection: String
    ): Boolean =
        updateFirestoreService.updateDocumentFirestore(clientDataMap, documentId, collection)

}
