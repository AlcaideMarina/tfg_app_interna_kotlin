package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateFeedUseCase @Inject constructor(
    private val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(feedDataMap: Map<String, Any?>, documentId: String): Boolean =
        updateFirestoreService.updateDocumentFirestore(feedDataMap, documentId, "material_feed")

}
