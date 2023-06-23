package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateHensUseCase @Inject constructor(
    private val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(hensDataMap: Map<String, Any?>, documentId: String): Boolean =
        updateFirestoreService.updateDocumentFirestore(hensDataMap, documentId, "material_hens")


}
