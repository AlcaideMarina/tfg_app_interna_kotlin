package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.services.GetThisMonthDailyFPCService
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.utils.MaterialUtils
import com.google.firebase.Timestamp
import javax.inject.Inject

class GetThisMonthDailyFPCUseCase @Inject constructor(
    private val getThisMonthDailyFPCService: GetThisMonthDailyFPCService
) {

    suspend operator fun invoke(
        initTimestamp: Timestamp,
        endTimestamp: Timestamp
    ): MonthlyFPCContainerModel {
        val fpcDataList = when (val result =
            getThisMonthDailyFPCService.getThisMonthDailyFPC(initTimestamp, endTimestamp)
                .getOrNull()) {
            null -> mutableListOf()
            else -> {
                val list = mutableListOf<FPCData>()
                if (!result.isEmpty && result.documents.size > 0) {
                    for (item in result.documents) {
                        if (item.data != null) {
                            val data = item.data!!
                            list.add(MaterialUtils.fpcMapToParcelable(data, item.id))
                        }
                    }
                }
                list
            }
        }.sortedBy { it.layingDatetime }.reversed()
        return MonthlyFPCContainerModel(
            initTimestamp,
            endTimestamp,
            fpcDataList
        )

    }

}
