package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.services.GetFirestoreDocumentWithDocIdService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class GetFeedResourcesWithIdUseCase @Inject constructor(
    private val getFirestoreDocumentWithDocIdService: GetFirestoreDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String): FeedResourcesData? {
        return when (val result =
            getFirestoreDocumentWithDocIdService.getDocumentWithDocId("material_feed", documentId)
                .getOrNull()) {
            null -> null
            else -> {
                if (result.data != null) {
                    MaterialUtils.feedMapToParcelable(result.data!!, result.id)
                } else null
            }
        }
    }

}
