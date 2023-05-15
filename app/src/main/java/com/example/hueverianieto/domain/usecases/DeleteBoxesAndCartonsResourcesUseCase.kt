package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteDocumentWithDocIdService
import javax.inject.Inject

class DeleteBoxesAndCartonsResourcesUseCase @Inject constructor(
    private val deleteDocumentWithDocIdService: DeleteDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String) =
        deleteDocumentWithDocIdService.deleteDocument("material_boxes_and_cartons", documentId)

}
