package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class NewHensResourcesUseCase @Inject constructor(
    private val newDocumentService: NewDocumentService
) {

    suspend operator fun invoke(hensResourcesData: HensResourcesData): Boolean {
        val dataMap = MaterialUtils.hensParcelableToMap(hensResourcesData)
        return when (newDocumentService.newDocument("material_hens", dataMap).getOrNull()) {
            null -> false
            else -> true
        }
    }

}
