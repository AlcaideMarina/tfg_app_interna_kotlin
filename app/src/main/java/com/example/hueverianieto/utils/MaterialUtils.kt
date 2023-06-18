package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.local.DBBoxesAndCartonsOrderFieldData
import com.example.hueverianieto.data.models.remote.*
import com.google.firebase.Timestamp

object MaterialUtils {

    fun hensMapToParcelable(
        data: MutableMap<String, Any?>,
        documentId: String?
    ): HensResourcesData {
        return HensResourcesData(
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["expense_datetime"] as Timestamp,
            data["hens_number"] as Long,
            data["shed_a"] as Long,
            data["shed_b"] as Long,
            data["total_price"] as Number,
        )
    }

    fun hensParcelableToMap(hensResourcesData: HensResourcesData): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = hensResourcesData.createdBy
        map["creation_datetime"] = hensResourcesData.creationDatetime
        map["deleted"] = hensResourcesData.deleted
        map["expense_datetime"] = hensResourcesData.expenseDatetime
        map["hens_number"] = hensResourcesData.hensNumber
        map["shed_a"] = hensResourcesData.shedA
        map["shed_b"] = hensResourcesData.shedB
        map["total_price"] = hensResourcesData.totalPrice

        return map
    }

    fun ewgMapToParcelable(
        data: MutableMap<String, Any?>,
        documentId: String?
    ): ElectricityWaterGasResourcesData {
        return ElectricityWaterGasResourcesData(
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["expense_datetime"] as Timestamp,
            data["notes"] as String,
            data["total_price"] as Number,
            data["type"] as Long
        )
    }

    fun ewgParcelableToMap(electricityWaterGasResourcesData: ElectricityWaterGasResourcesData): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = electricityWaterGasResourcesData.createBy
        map["creation_datetime"] = electricityWaterGasResourcesData.creationDatetime
        map["deleted"] = electricityWaterGasResourcesData.deleted
        map["expense_datetime"] = electricityWaterGasResourcesData.expenseDatetime
        map["notes"] = electricityWaterGasResourcesData.notes
        map["total_price"] = electricityWaterGasResourcesData.totalPrice
        map["type"] = electricityWaterGasResourcesData.type

        return map
    }

    fun feedMapToParcelable(data: Map<String, Any?>, documentId: String?): FeedResourcesData {
        return FeedResourcesData(
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["expense_datetime"] as Timestamp,
            data["kilos"] as Number,
            data["total_price"] as Number
        )
    }

    fun feedParcelableToMap(feedResourcesData: FeedResourcesData): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = feedResourcesData.createdBy
        map["creation_datetime"] = feedResourcesData.creationDatetime
        map["deleted"] = feedResourcesData.deleted
        map["expense_datetime"] = feedResourcesData.expenseDatetime
        map["kilos"] = feedResourcesData.kilos
        map["total_price"] = feedResourcesData.totalPrice

        return map
    }

    fun boxesAndCartonsMapToParcelable(
        data: Map<String, Any?>,
        documentId: String?
    ): BoxesAndCartonsResourcesData {
        return BoxesAndCartonsResourcesData(
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["expense_datetime"] as Timestamp,
            data["order"] as Map<String, Number?>,
            data["total_price"] as Number
        )
    }

    fun boxesAndCartonsParcelableToMap(boxesAndCartonsResourcesData: BoxesAndCartonsResourcesData): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = boxesAndCartonsResourcesData.createdBy
        map["creation_datetime"] = boxesAndCartonsResourcesData.creationDatetime
        map["deleted"] = boxesAndCartonsResourcesData.deleted
        map["expense_datetime"] = boxesAndCartonsResourcesData.expenseDatetime
        map["order"] = boxesAndCartonsResourcesData.order
        map["total_price"] = boxesAndCartonsResourcesData.totalPrice

        return map
    }

    fun getBCOrderSummary(dbOrderFieldData: DBBoxesAndCartonsOrderFieldData): String {

        val list: MutableList<String> = mutableListOf()

        if (dbOrderFieldData.box != 0) list.add(dbOrderFieldData.box.toString() + " cajas")
        if (dbOrderFieldData.xlCarton != 0) list.add(dbOrderFieldData.xlCarton.toString() + " cartones XL")
        if (dbOrderFieldData.lCarton != 0) list.add(dbOrderFieldData.lCarton.toString() + " cartones L")
        if (dbOrderFieldData.mCarton != 0) list.add(dbOrderFieldData.mCarton.toString() + " cartones M")
        if (dbOrderFieldData.sCarton != 0) list.add(dbOrderFieldData.sCarton.toString() + " cartones S")

        var summary = ""
        for (item in list) {
            summary += item
            if (item != list[list.size - 1]) summary += " - "
        }

        return summary
    }

    fun bcOrderToDBBoxesAndCartonsOrderModel(bcData: BoxesAndCartonsResourcesData): DBBoxesAndCartonsOrderFieldData {

        val order = bcData.order
        val data = DBBoxesAndCartonsOrderFieldData()

        if (order.containsKey("box")) {
            data.box = order["box"]
        }
        if (order.containsKey("xl_carton")) {
            data.xlCarton = order["xl_carton"]
        }
        if (order.containsKey("l_carton")) {
            data.lCarton = order["l_carton"]
        }
        if (order.containsKey("m_carton")) {
            data.mCarton = order["m_carton"]
        }
        if (order.containsKey("s_carton")) {
            data.sCarton = order["s_carton"]
        }

        return data

    }

    fun parseDBBoxesAndCartonsOrderFieldDataToMap(data: DBBoxesAndCartonsOrderFieldData):
            Map<String, Number?> {
        val map = mutableMapOf<String, Number?>()
        if (data.box != null && data.box!!.toInt() != 0) {
            map["box"] = data.box
        }
        if (data.xlCarton != null && data.xlCarton!!.toInt() != 0) {
            map["xl_carton"] = data.xlCarton
        }
        if (data.lCarton != null && data.lCarton!!.toInt() != 0) {
            map["l_carton"] = data.lCarton
        }
        if (data.mCarton != null && data.mCarton!!.toInt() != 0) {
            map["m_carton"] = data.mCarton
        }
        if (data.sCarton != null && data.sCarton!!.toInt() != 0) {
            map["s_carton"] = data.sCarton
        }
        return map
    }

    fun fpcMapToParcelable(data: Map<String, Any?>, documentId: String?): FPCData {
        return FPCData(
            data["accepted_eggs"] as Long,
            data["best_before_datetime"] as Timestamp,
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["issue_datetime"] as Timestamp,
            data["laying_datetime"] as Timestamp,
            data["lot"] as Long,
            data["packing_datetime"] as Timestamp,
            data["rejected_eggs"] as Long
        )
    }

}
