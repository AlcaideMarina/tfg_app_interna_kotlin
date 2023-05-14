package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.services.GetFirestoreDocumentWithDocIdService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class GetEWGResourcesWithIdUseCase @Inject constructor(
    private val getFirestoreDocumentWithDocIdService: GetFirestoreDocumentWithDocIdService
) {

    suspend operator fun invoke(documentId: String) : ElectricityWaterGasResourcesData? {
        return when(val result = getFirestoreDocumentWithDocIdService.getDocumentWithDocId("material_electricity_water_gas", documentId).getOrNull()) {
            null -> null
            else -> {
                if (result.data != null) {
                    MaterialUtils.ewgMapToParcelable(result.data!!, result.id)
                } else null
            }
        }
    }

}
