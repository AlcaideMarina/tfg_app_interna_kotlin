package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.services.GetFirestoreDocumentWithDocIdService
import com.example.hueverianieto.utils.InternalUserUtils
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class GetHenResourcesWithIdUseCase @Inject constructor(
    private val getFirestoreDocumentWithDocIdService: GetFirestoreDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String) : HensResourcesData? {
        return when(val result = getFirestoreDocumentWithDocIdService.getDocumentWithDocId("material_hens", documentId).getOrNull()) {
            null -> null
            else -> {
                if (result.data != null) {
                    MaterialUtils.hensMapToParcelable(result.data!!, result.id)
                } else null
            }
        }
    }

}
