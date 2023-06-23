package com.example.hueverianieto.utils

import com.example.hueverianieto.R

object Constants {

    const val loginNetworkError: String =
        "A network error (such as timeout, interrupted connection or unreachable host) has occurred."
    const val loginBadFormattedEmailError: String = "The email address is badly formatted."
    const val loginNoUserRecordedError: String =
        "There is no user record corresponding to this identifier. The user may have been deleted."
    const val loginInvalidPasswordError: String =
        "The password is invalid or the user does not have a password."

    val roles: Map<Int, Int> = mapOf(
        R.string.warehouse_job to 0,
        R.string.boss_job to 1,
        R.string.delivery_job to 2,
        R.string.office_job to 3,
        R.string.farm_job to 4
    )

    val orderStatus: Map<Int, Int> = mapOf(
        R.string.price_pending to 0,
        R.string.backordered to 1,
        R.string.in_delivery to 2,
        R.string.delivered to 3,
        R.string.delivery_attempt to 4,
        R.string.cancelled to 5
    )

    val paymentMethod: Map<Int, Int> = mapOf(
        R.string.in_cash to 0,
        R.string.per_receipt to 1,
        R.string.transfer to 2
    )

    val ewgTypes: Map<Int, Int> = mapOf(
        R.string.electricity to 0,
        R.string.water to 1,
        R.string.gas to 2
    )

    const val dateFormat = "dd/MM/yyyy"

}
