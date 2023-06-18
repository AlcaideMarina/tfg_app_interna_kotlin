package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.services.GetFirestoreDocumentWithDocIdService
import com.example.hueverianieto.utils.FarmUtils
import javax.inject.Inject

class GetFPCWithIdUseCase @Inject constructor(
    private val getFirestoreDocumentWithDocIdService: GetFirestoreDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String): FPCData? {
        return when (val result = getFirestoreDocumentWithDocIdService.getDocumentWithDocId(
            "final_product_control",
            documentId
        ).getOrNull()) {
            null -> null
            else -> {
                if (result.data != null) {
                    FarmUtils.fpcMapToParcelable(result.data!!, result.id)
                } else null
            }
        }
    }

}
