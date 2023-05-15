package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.services.GetFirestoreDocumentWithDocIdService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class GetBoxesAndCartonsResourcesWithIdUseCase @Inject constructor(
    private val getFirestoreDocumentWithDocIdService: GetFirestoreDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String) : BoxesAndCartonsResourcesData? {
        return when(val result = getFirestoreDocumentWithDocIdService.getDocumentWithDocId("material_boxes_and_cartons", documentId).getOrNull()) {
            null -> null
            else -> {
                if (result.data != null) {
                    MaterialUtils.boxesAndCartonsMapToParcelable(result.data!!, result.id)
                } else null
            }
        }
    }
}
