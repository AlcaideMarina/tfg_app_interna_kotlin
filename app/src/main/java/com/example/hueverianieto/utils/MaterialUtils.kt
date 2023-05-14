package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.google.firebase.Timestamp

object MaterialUtils {

    fun hensMapToParcelable(data: MutableMap<String, Any?>, documentId: String?): HensResourcesData {
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

    fun hensParcelableToMap(hensResourcesData: HensResourcesData) : Map<String, Any?> {
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

    fun ewgMapToParcelable(data: MutableMap<String, Any?>, documentId: String?) : ElectricityWaterGasResourcesData {
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

    fun ewgParcelableToMap(electricityWaterGasResourcesData: ElectricityWaterGasResourcesData) : Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = electricityWaterGasResourcesData.createBy
        map["creation_datetime"] = electricityWaterGasResourcesData.creationDatatime
        map["deleted"] = electricityWaterGasResourcesData.deleted
        map["expense_datetime"] = electricityWaterGasResourcesData.expenseDatetime
        map["notes"] = electricityWaterGasResourcesData.notes
        map["total_price"] = electricityWaterGasResourcesData.totalPrice
        map["type"] = electricityWaterGasResourcesData.type

        return map
    }

    fun feedMapToParcelable(data: Map<String, Any?>, documentId: String?) : FeedResourcesData {
        return FeedResourcesData(
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["deleted"] as Boolean,
            documentId,
            data["expense_datetime"] as Timestamp,
            data["kilos"] as Number,
            data["total_price"] as Long)
    }

    fun feedParcelableToMap(feedResourcesData: FeedResourcesData) : Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["created_by"] = feedResourcesData.createdBy
        map["creation_datetime"] = feedResourcesData.creationDatetime
        map["deleted"] = feedResourcesData.deleted
        map["expense_datetime"] = feedResourcesData.expenseDatetime
        map["kilos"] = feedResourcesData.kilos
        map["total_price"] = feedResourcesData.totalPrice

        return map
    }

}