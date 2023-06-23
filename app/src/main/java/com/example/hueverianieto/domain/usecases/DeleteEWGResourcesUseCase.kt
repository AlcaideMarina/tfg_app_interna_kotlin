package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteDocumentWithDocIdService
import javax.inject.Inject

class DeleteEWGResourcesUseCase @Inject constructor(
    private val deleteDocumentWithDocIdService: DeleteDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String) =
        deleteDocumentWithDocIdService.deleteDocument(
            "material_electricity_water_gas", documentId
        )

}
