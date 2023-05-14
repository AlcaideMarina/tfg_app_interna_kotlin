package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class NewElectricityWaterGasResourcesUseCase @Inject constructor(
    private val newDocumentService: NewDocumentService
) {

    suspend operator fun invoke(ewgResourcesData: ElectricityWaterGasResourcesData) : Boolean {
        val dataMap = MaterialUtils.ewgParcelableToMap(ewgResourcesData)
        return when (newDocumentService.newDocument("material_electricity_water_gas", dataMap).getOrNull()) {
            null -> false
            else -> true
        }
    }

}