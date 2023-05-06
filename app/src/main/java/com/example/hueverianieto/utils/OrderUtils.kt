package com.example.hueverianieto.utils

import com.example.hueverianieto.data.models.local.DBOrderFieldData
import com.example.hueverianieto.data.models.local.GridTextItemData
import com.example.hueverianieto.data.models.remote.OrderData
import com.google.firebase.Timestamp

object OrderUtils {

    fun parcelableToMap(orderData: OrderData) : MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map["approximate_delivery_datetime"] = orderData.approxDeliveryDatetime
        map["client_id"] = orderData.clientId
        map["company"] = orderData.company
        map["created_by"] = orderData.createdBy
        map["delivery_datetime"] = orderData.deliveryDatetime
        map["delivery_dni"] = orderData.deliveryDni
        map["delivery_note"] = orderData.deliveryNote
        map["delivery_person"] = orderData.deliveryPerson
        map["notes"] = orderData.notes
        map["order"] = orderData.order
        map["order_datetime"] = orderData.orderDatetime
        map["order_id"] = orderData.orderId
        map["paid"] = orderData.paid
        map["payment_method"] = orderData.paymentMethod
        map["status"] = orderData.status
        map["total_price"] = orderData.totalPrice
        return map
    }

    fun mapToParcelable(data: MutableMap<String, Any?>, documentId: String) : OrderData {
        return OrderData(
            data["approximate_delivery_datetime"] as Timestamp,
            data["client_id"] as Long,
            data["company"] as String,
            data["created_by"] as String,
            data["delivery_datetime"] as Timestamp?,
            data["delivery_dni"] as String?,
            data["delivery_note"] as Long?,
            data["delivery_person"] as String?,
            data["notes"] as String?,
            data["order"] as Map<String, Map<String, Number>>,
            data["order_datetime"] as Timestamp,
            data["order_id"] as Long,
            data["paid"] as Boolean,
            data["payment_method"] as Long,
            data["status"] as Long,
            data["total_price"] as Number?,
            documentId
        )
    }

    fun getOrderSummary(dbOrderFieldData: DBOrderFieldData) : String {

        val list : MutableList<String> = mutableListOf()

        if (dbOrderFieldData.xlBoxQuantity != 0) list.add(dbOrderFieldData.xlBoxQuantity.toString() + " cajas XL")
        if (dbOrderFieldData.xlDozenQuantity != 0) list.add(dbOrderFieldData.xlDozenQuantity.toString() + " docenas XL")
        if (dbOrderFieldData.lBoxQuantity != 0) list.add(dbOrderFieldData.lBoxQuantity.toString() + " cajas L")
        if (dbOrderFieldData.lDozenQuantity != 0) list.add(dbOrderFieldData.lDozenQuantity.toString() + " docenas L")
        if (dbOrderFieldData.mBoxQuantity != 0) list.add(dbOrderFieldData.mBoxQuantity.toString() + " cajas M")
        if (dbOrderFieldData.mDozenQuantity != 0) list.add(dbOrderFieldData.mDozenQuantity.toString() + " docenas M")
        if (dbOrderFieldData.sBoxQuantity != 0) list.add(dbOrderFieldData.sBoxQuantity.toString() + " cajas S")
        if (dbOrderFieldData.sDozenQuantity != 0) list.add(dbOrderFieldData.sDozenQuantity.toString() + " docenas S")

        var summary = ""
        for (item in list) {
            summary += item
            if (item != list[list.size - 1]) summary += " - "
        }

        return summary
    }

    fun orderDataToBDOrderModel(orderData: OrderData) : DBOrderFieldData {

        val order = orderData.order
        val dbOrderFieldData = DBOrderFieldData()

        if (order.containsKey("xl_box")) {
            val xlBox = order["xl_box"]!!
            dbOrderFieldData.xlBoxPrice = xlBox["price"]
            dbOrderFieldData.xlBoxQuantity = xlBox["quantity"]?.toLong()
        }
        if (order.containsKey("xl_dozen")) {
            val xlDozen = order["xl_dozen"]!!
            dbOrderFieldData.xlDozenPrice = xlDozen["price"]
            dbOrderFieldData.xlDozenQuantity = xlDozen["quantity"]?.toLong()
        }

        if (order.containsKey("l_box")) {
            val lBox = order["l_box"]!!
            dbOrderFieldData.lBoxPrice = lBox["price"]
            dbOrderFieldData.lBoxQuantity = lBox["quantity"]?.toLong()
        }
        if (order.containsKey("l_dozen")) {
            val lDozen = order["l_dozen"]!!
            dbOrderFieldData.lDozenPrice = lDozen["price"]
            dbOrderFieldData.lDozenQuantity = lDozen["quantity"]?.toLong()
        }

        if (order.containsKey("m_box")) {
            val mBox = order["m_box"]!!
            dbOrderFieldData.mBoxPrice = mBox["price"]
            dbOrderFieldData.mBoxQuantity = mBox["quantity"]?.toLong()
        }
        if (order.containsKey("m_dozen")) {
            val mDozen = order["m_dozen"]!!
            dbOrderFieldData.mDozenPrice = mDozen["price"]
            dbOrderFieldData.mDozenQuantity = mDozen["quantity"]?.toLong()
        }

        if (order.containsKey("s_box")) {
            val sBox = order["s_box"]!!
            dbOrderFieldData.sBoxPrice = sBox["price"]
            dbOrderFieldData.sBoxQuantity = sBox["quantity"]?.toLong()
        }
        if (order.containsKey("s_dozen")) {
            val sDozen = order["s_dozen"]!!
            dbOrderFieldData.sDozenPrice = sDozen["price"]
            dbOrderFieldData.sDozenQuantity = sDozen["quantity"]?.toLong()
        }

        return dbOrderFieldData
    }

    fun getOrderDataGridModel(orderData: OrderData) : List<GridTextItemData> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(
            GridTextItemData(0,
                true, "XL"
            ),
            GridTextItemData(1,
                true, "Docena:"
            ),
            GridTextItemData(2,
                false, null, false, response = dbOrderModel.xlDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(3,
                true, (dbOrderModel.xlDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(4,
                true, "Caja:"
            ),
            GridTextItemData(5,
                false, null, false, response = dbOrderModel.xlBoxQuantity.toString() + "   uds."
            ),
            GridTextItemData(6,
                true, (dbOrderModel.xlBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(7,
                true, "L"
            ),
            GridTextItemData(8,
                true, "Docena:"
            ),
            GridTextItemData(9,
                false, null, false, response = dbOrderModel.lDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(10,
                true, (dbOrderModel.lDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(11,
                true, "Caja:"
            ),
            GridTextItemData(12,
                false, null, false, response = dbOrderModel.lBoxQuantity.toString() + "   uds."
            ),
            GridTextItemData(13,
                true, (dbOrderModel.lBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(14,
                true, "M"
            ),
            GridTextItemData(15,
                true, "Docena:"
            ),
            GridTextItemData(16,
                false, null, false, response = dbOrderModel.mDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(17,
                true, (dbOrderModel.mDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(18,
                true, "Caja:"
            ),
            GridTextItemData(19,
                false, null, false, response = dbOrderModel.mBoxQuantity.toString() + "   uds."
            ),
            GridTextItemData(20,
                true, (dbOrderModel.mBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(21,
                true, "S"
            ),
            GridTextItemData(22,
                true, "Docena:"
            ),
            GridTextItemData(23,
                false, null, false, response = dbOrderModel.sDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(24,
                true, (dbOrderModel.sDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(25,
                true, "Caja:"
            ),
            GridTextItemData(26,
                false, null, false, response = dbOrderModel.sBoxQuantity.toString() + "   uds."
            ),
            GridTextItemData(27,
                true, (dbOrderModel.sBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
        )
    }

}