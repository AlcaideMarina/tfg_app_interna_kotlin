package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateBoxesAndCartonsUseCase @Inject constructor(
    private val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(bcDataMpa: Map<String, Any?>, documentId: String): Boolean =
        updateFirestoreService.updateDocumentFirestore(
            bcDataMpa,
            documentId,
            "material_boxes_and_cartons"
        )

}
