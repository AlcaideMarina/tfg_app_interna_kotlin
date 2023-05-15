package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.UpdateFirestoreService
import javax.inject.Inject

class UpdateEWGUseCase @Inject constructor(
    private val updateFirestoreService: UpdateFirestoreService
) {

    suspend operator fun invoke(ewgDataMap: Map<String, Any?>, documentId: String) : Boolean =
        updateFirestoreService.updateDocumentFirestore(ewgDataMap, documentId, "material_electricity_water_gas")

}
