package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateFPCUseCase @Inject constructor(
    val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(fpcDataMap: Map<String, Any?>, documentId: String) : Boolean =
        updateFirestoreService.updateDocumentFirestore(fpcDataMap, documentId, "final_product_control")

}
