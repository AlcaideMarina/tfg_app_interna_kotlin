package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.google.firebase.Timestamp

object FarmUtils {

    fun fpcMapToParcelable(data: MutableMap<String, Any?>, documentId: String?): FPCData {
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
            data["rejected_eggs"] as Long,
        )
    }

    fun fpcParcelableToMap(fpcData: FPCData) : Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["accepted_eggs"] = fpcData.acceptedEggs
        map["best_before_datetime"] = fpcData.bestBeforeDatetime
        map["created_by"] = fpcData.createdBy
        map["creation_datetime"] = fpcData.creationDatetime
        map["deleted"] = fpcData.deleted
        map["issue_datetime"] = fpcData.issueDatetime
        map["laying_datetime"] = fpcData.layingDatetime
        map["lot"] = fpcData.lot
        map["packing_datetime"] = fpcData.packingDatetime
        map["rejected_eggs"] = fpcData.rejectedEggs

        return map
    }

    fun monitoringCompanySituationMapToParcelable(data: MutableMap<String, Any?>, documentId: String?): MonitoringCompanySituationData {
        return MonitoringCompanySituationData(
            data["broken_eggs"] as Long,
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            documentId,
            data["hens"] as Map<String, Long>,
            data["l_eggs"] as Map<String, Long>,
            data["m_eggs"] as Map<String, Long>,
            data["s_eggs"] as Map<String, Long>,
            data["situation_datetime"] as Timestamp,
            data["l_eggs"] as Map<String, Long>,
        )
    }

    fun monitoringCompanySituationParcelableToMap(monitoringCompanySituationData: MonitoringCompanySituationData) : Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["broken_eggs"] = monitoringCompanySituationData.brokenEggs
        map["created_by"] = monitoringCompanySituationData.createdBy
        map["creation_datetime"] = monitoringCompanySituationData.creationDatetime
        map["hens"] = monitoringCompanySituationData.hens
        map["l_eggs"] = monitoringCompanySituationData.lEggs
        map["m_eggs"] = monitoringCompanySituationData.mEggs
        map["s_eggs"] = monitoringCompanySituationData.sEggs
        map["situation_datetime"] = monitoringCompanySituationData.situationDatetime
        map["l_eggs"] = monitoringCompanySituationData.lEggs

        return map
    }

}