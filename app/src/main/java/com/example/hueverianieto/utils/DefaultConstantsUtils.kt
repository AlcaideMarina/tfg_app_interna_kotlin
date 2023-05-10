package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.local.EggPricesData

object DefaultConstantsUtils {

    fun eggPricesParcelizeToMap(eggPricesData: EggPricesData) : Map<String, Any> {
        val map = mutableMapOf<String, Any>()
        map["xl_box"] = eggPricesData.xlBox ?: 0
        map["xl_dozen"] = eggPricesData.xlDozen ?: 0
        map["l_box"] = eggPricesData.lBox ?: 0
        map["l_dozen"] = eggPricesData.lDozen ?: 0
        map["m_box"] = eggPricesData.mBox ?: 0
        map["m_dozen"] = eggPricesData.mDozen ?: 0
        map["s_box"] = eggPricesData.sBox ?: 0
        map["s_dozen"] = eggPricesData.sDozen ?: 0
        return map
    }

}