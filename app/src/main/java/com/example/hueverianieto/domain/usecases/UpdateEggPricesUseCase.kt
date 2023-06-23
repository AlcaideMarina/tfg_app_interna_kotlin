package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.services.UpdateConstantService
import com.example.hueverianieto.utils.DefaultConstantsUtils
import javax.inject.Inject

class UpdateEggPricesUseCase @Inject constructor(
    private val updateConstantService: UpdateConstantService
) {

    suspend operator fun invoke(eggPricesData: EggPricesData, userDocumentId: String): Boolean {
        val map = DefaultConstantsUtils.eggPricesParcelizeToMap(eggPricesData)
        return when (val result =
            updateConstantService.updateConstantService(map, "egg_prices", userDocumentId)
                .getOrNull()) {
            null -> false
            else -> true
        }
    }

}
