package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class NewBoxesAndCartonsResourcesUseCase @Inject constructor(
    private val newDocumentService: NewDocumentService
) {

    suspend operator fun invoke(bcResourcesData: BoxesAndCartonsResourcesData) : Boolean {
        val dataMap = MaterialUtils.boxesAndCartonsParcelableToMap(bcResourcesData)
        return when(newDocumentService.newDocument("material_boxes_and_cartons", dataMap)
            .getOrNull()) {
            null -> false
            else -> true
        }
    }

}
