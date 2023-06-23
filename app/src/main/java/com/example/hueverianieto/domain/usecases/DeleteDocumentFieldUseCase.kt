package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.DeleteDocumentFieldService
import javax.inject.Inject

class DeleteDocumentFieldUseCase @Inject constructor(
    private val deleteDocumentFieldService: DeleteDocumentFieldService
) {

    suspend operator fun invoke(documentId: String, collection: String): Boolean =
        deleteDocumentFieldService.deleteDocumentField(documentId, collection)

}