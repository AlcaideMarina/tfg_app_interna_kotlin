package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.remote.InternalUserData

// TODO: Sacar constantes

object InternalUserUtils {

    fun checkErrorMap(data: MutableMap<String, Any?>?): String? {
        return if (data == null) {
            "empty input map"
        } else if (data.containsKey("bank_account") && data.containsKey("city") &&
            data.containsKey("created_by") &&
            data.containsKey("deleted") && data.containsKey("direction") && data.containsKey("dni")
            && data.containsKey("email") && data.containsKey("id") && data.containsKey("name") &&
            data.containsKey("phone") && data.containsKey("position") &&
            data.containsKey("postal_code") && data.containsKey("province") &&
            data.containsKey("ss_number") && data.containsKey("surname") && data.containsKey("uid")
            && data.containsKey("user")
        ) {
            null
        } else {
            "missing fields"
        }
    }

    fun mapToParcelable(data: MutableMap<String, Any?>, documentId: String?): InternalUserData {
        return InternalUserData(
            data["bank_account"] as String,
            data["city"] as String,
            data["created_by"] as String,
            data["deleted"] as Boolean,
            data["direction"] as String,
            data["dni"] as String,
            data["email"] as String,
            data["id"] as Long,
            data["name"] as String,
            data["phone"] as Long,
            data["position"] as Long,
            data["postal_code"] as Long,
            data["province"] as String,
            data["ss_number"] as Long,
            data["surname"] as String,
            data["uid"] as String,
            data["user"] as String,
            documentId
        )
    }

    fun parcelableToMap(internalUserData: InternalUserData): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        map["bank_account"] = internalUserData.bankAccount
        map["city"] = internalUserData.city
        map["created_by"] = internalUserData.createdBy
        map["deleted"] = internalUserData.deleted
        map["direction"] = internalUserData.direction
        map["dni"] = internalUserData.dni
        map["email"] = internalUserData.email
        map["id"] = internalUserData.id
        map["name"] = internalUserData.name
        map["phone"] = internalUserData.phone
        map["position"] = internalUserData.position
        map["postal_code"] = internalUserData.postalCode
        map["province"] = internalUserData.province
        map["ss_number"] = internalUserData.ssNumber
        map["surname"] = internalUserData.surname
        map["uid"] = internalUserData.uid
        map["user"] = internalUserData.user

        return map
    }

}
