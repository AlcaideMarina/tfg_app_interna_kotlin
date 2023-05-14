package com.example.hueverianieto.utils

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

}