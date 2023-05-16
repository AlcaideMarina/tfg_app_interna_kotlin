package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.services.NewDocumentService
import com.example.hueverianieto.utils.FarmUtils
import javax.inject.Inject

class NewFinalProductControlUseCase @Inject constructor(
    private val newDocumentService: NewDocumentService
) {

    suspend operator fun invoke(fpcData: FPCData) : Boolean {
        val dataMap = FarmUtils.fpcParcelableToMap(fpcData)
        return when (newDocumentService.newDocument("final_product_control", dataMap).getOrNull()) {
            null -> false
            else -> true
        }
    }

}
