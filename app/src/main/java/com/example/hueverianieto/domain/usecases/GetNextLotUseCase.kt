package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.services.GetNextLotService
import com.example.hueverianieto.utils.MaterialUtils
import javax.inject.Inject

class GetNextLotUseCase @Inject constructor(
    private val getNextLotService: GetNextLotService
) {

    suspend operator fun invoke(): Int {
        return when (val result = getNextLotService.getNextLot().getOrNull()) {
            null -> 0
            else -> {
                if (!result.isEmpty && result.documents.size > 0) {
                    val doc = result.documents[0]
                    val fpc = MaterialUtils.fpcMapToParcelable(doc.data!!, doc.id)
                    fpc.lot.toInt()
                } else 0
            }
        }
    }

}
