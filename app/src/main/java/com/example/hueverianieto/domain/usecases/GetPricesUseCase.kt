package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.services.GetConstantService
import javax.inject.Inject

class GetPricesUseCase @Inject constructor(
    private val getConstantService: GetConstantService
) {

    suspend operator fun invoke(): EggPricesData? {
        return when (val result = getConstantService.getConstant("egg_prices").getOrNull()) {
            null -> null
            else -> {
                if (!result.isEmpty && result.documents.isNotEmpty()
                    && result.documents[0].data != null
                ) {
                    val values = result.documents[0].data!!["values"]!! as Map<String, Number>
                    EggPricesData(
                        values["xl_box"],
                        values["xl_dozen"],
                        values["l_box"],
                        values["l_dozen"],
                        values["m_box"],
                        values["m_dozen"],
                        values["s_box"],
                        values["s_dozen"]
                    )
                } else null
            }
        }
    }

}
