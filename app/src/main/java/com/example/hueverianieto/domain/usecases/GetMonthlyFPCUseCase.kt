package com.example.hueverianieto.domain.usecases

import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.services.GetAllDocumentsFromCollectionService
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCContainerItemModel
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import com.example.hueverianieto.utils.MaterialUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import java.util.*
import javax.inject.Inject

class GetMonthlyFPCUseCase @Inject constructor(
    private val getAllDocumentsFromCollectionService: GetAllDocumentsFromCollectionService
) {

    suspend operator fun invoke() : List<MonthlyFPCContainerModel?> {
        val fpcDataList = when(val result = getAllDocumentsFromCollectionService.getDocumentsFromCollection("final_product_control").getOrNull()) {
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

        val firstDate = fpcDataList[0].layingDatetime.toDate()

        val calendar = Calendar.getInstance()
        calendar.time = firstDate
        var m = (calendar.get(Calendar.MONTH) + 1).toString()
        while (m.length < 2) m = "0" + m
        var y = calendar.get(Calendar.YEAR).toString()
        while (y.length < 4) y = "0" + y

        // Creamos fecha inicial y final
        var initDateTimestamp = Utils.parseStringToTimestamp(
            "01/$m/$y"
        )
        var endDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = 1))


        val list: MutableList<MonthlyFPCContainerModel> = mutableListOf()
        var monthlyFPCDataList = mutableListOf<FPCData>()
        for (item in fpcDataList) {
            if (initDateTimestamp > item.layingDatetime) {
                val monthlyFPCContainerModel = MonthlyFPCContainerModel(
                    initDateTimestamp,
                    endDateTimestamp,
                    monthlyFPCDataList
                )
                list.add(monthlyFPCContainerModel)

                val newCal = Calendar.getInstance()
                newCal.time = item.layingDatetime.toDate()
                var m = (newCal.get(Calendar.MONTH) + 1).toString()
                while (m.length < 2) m = "0" + m
                var y = newCal.get(Calendar.YEAR).toString()
                while (y.length < 4) y = "0" + y

                initDateTimestamp = Utils.parseStringToTimestamp("01/$m/$y")
                endDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = 1))
                monthlyFPCDataList = mutableListOf()
            }
            monthlyFPCDataList.add(item)

            if (fpcDataList.last() == item) {
                val monthlyFPCContainerModel = MonthlyFPCContainerModel(
                    initDateTimestamp,
                    endDateTimestamp,
                    monthlyFPCDataList
                )
                list.add(monthlyFPCContainerModel)

                val newCal = Calendar.getInstance()
                newCal.time = item.layingDatetime.toDate()
                var m = (newCal.get(Calendar.MONTH) + 1).toString()
                while (m.length < 2) m = "0" + m
                var y = newCal.get(Calendar.YEAR).toString()
                while (y.length < 4) y = "0" + y

                initDateTimestamp = Utils.parseStringToTimestamp("01/$m/$y")
                endDateTimestamp = Timestamp(Utils.addToDate(initDateTimestamp.toDate(), monthsToAdd = 1))
                monthlyFPCDataList = mutableListOf()
            }
        }
        return list
    }

}
