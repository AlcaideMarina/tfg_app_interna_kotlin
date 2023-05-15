package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.services.GetEWGService
import javax.inject.Inject

class GetEWGUseCase @Inject constructor(
    private val getEWGService: GetEWGService
) {

    suspend operator fun invoke() : List<ElectricityWaterGasResourcesData?>? = getEWGService.getEWG()

}
