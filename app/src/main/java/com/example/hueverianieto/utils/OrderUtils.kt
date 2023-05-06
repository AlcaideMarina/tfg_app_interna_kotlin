package com.example.hueverianieto.utils

import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.models.local.DBOrderFieldData
import com.example.hueverianieto.data.models.local.GridTextItemData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
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
        map["lot"] = orderData.lot
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
            data["lot"] as String?,
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

    fun getOrderDataGridModel(orderData: OrderData, isEnable : Boolean) : List<GridTextItemData> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(
            GridTextItemData(0,
                true, "XL"
            ),
            GridTextItemData(1,
                true, "Docena:"
            ),
            GridTextItemData(2,
                false, null, isEnable, response = dbOrderModel.xlDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(3,
                true, (dbOrderModel.xlDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(4,
                true, "Caja:"
            ),
            GridTextItemData(5,
                false, null, isEnable, response = dbOrderModel.xlBoxQuantity.toString() + "   uds."
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
                false, null, isEnable, response = dbOrderModel.lDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(10,
                true, (dbOrderModel.lDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(11,
                true, "Caja:"
            ),
            GridTextItemData(12,
                false, null, isEnable, response = dbOrderModel.lBoxQuantity.toString() + "   uds."
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
                false, null, isEnable, response = dbOrderModel.mDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(17,
                true, (dbOrderModel.mDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(18,
                true, "Caja:"
            ),
            GridTextItemData(19,
                false, null, isEnable, response = dbOrderModel.mBoxQuantity.toString() + "   uds."
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
                false, null, isEnable, response = dbOrderModel.sDozenQuantity.toString() + "   uds."
            ),
            GridTextItemData(24,
                true, (dbOrderModel.sDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(25,
                true, "Caja:"
            ),
            GridTextItemData(26,
                false, null, isEnable, response = dbOrderModel.sBoxQuantity.toString() + "   uds."
            ),
            GridTextItemData(27,
                true, (dbOrderModel.sBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
        )
    }

    fun getOrderDataModifyGridModel(orderData: OrderData) : List<GridTextItemData> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(
            GridTextItemData(0,
                true, "XL"
            ),
            GridTextItemData(1,
                true, "Docena:"
            ),
            GridTextItemData(2,
                false, null, response = dbOrderModel.xlDozenQuantity.toString()
            ),
            GridTextItemData(3,
                true, (dbOrderModel.xlDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(4,
                true, "Caja:"
            ),
            GridTextItemData(5,
                false, null, response = dbOrderModel.xlBoxQuantity.toString()
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
                false, null, response = dbOrderModel.lDozenQuantity.toString()
            ),
            GridTextItemData(10,
                true, (dbOrderModel.lDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(11,
                true, "Caja:"
            ),
            GridTextItemData(12,
                false, null, response = dbOrderModel.lBoxQuantity.toString()
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
                false, null, response = dbOrderModel.mDozenQuantity.toString()
            ),
            GridTextItemData(17,
                true, (dbOrderModel.mDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(18,
                true, "Caja:"
            ),
            GridTextItemData(19,
                false, null, response = dbOrderModel.mBoxQuantity.toString()
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
                false, null, response = dbOrderModel.sDozenQuantity.toString()
            ),
            GridTextItemData(24,
                true, (dbOrderModel.sDozenPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(25,
                true, "Caja:"
            ),
            GridTextItemData(26,
                false, null, response = dbOrderModel.sBoxQuantity.toString()
            ),
            GridTextItemData(27,
                true, (dbOrderModel.sBoxPrice ?: "-").toString() + " €/ud", isTextLeft = false
            ),
        )
    }

    fun parseDBOrderFieldDataToMap(dbOrderFieldData: DBOrderFieldData) :
            Map<String, Map<String, Number?>> {
        val map = mutableMapOf<String, Map<String, Number?>>()
        if(dbOrderFieldData.xlBoxQuantity != null) {
            map["xl_box"] = mapOf(
                "price" to dbOrderFieldData.xlBoxPrice,
                "quantity" to dbOrderFieldData.xlBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.xlDozenQuantity != null) {
            map["xl_dozen"] = mapOf(
                "price" to dbOrderFieldData.xlDozenPrice,
                "quantity" to dbOrderFieldData.xlDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.lBoxQuantity != null) {
            map["l_box"] = mapOf(
                "price" to dbOrderFieldData.lBoxPrice,
                "quantity" to dbOrderFieldData.lBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.lDozenQuantity != null) {
            map["l_dozen"] = mapOf(
                "price" to dbOrderFieldData.lDozenPrice,
                "quantity" to dbOrderFieldData.lDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.mBoxQuantity != null) {
            map["m_box"] = mapOf(
                "price" to dbOrderFieldData.mBoxPrice,
                "quantity" to dbOrderFieldData.mBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.mDozenQuantity != null) {
            map["m_dozen"] = mapOf(
                "price" to dbOrderFieldData.mDozenPrice,
                "quantity" to dbOrderFieldData.mDozenQuantity as Int
            )
        }
        if(dbOrderFieldData.sBoxQuantity != null) {
            map["s_box"] = mapOf(
                "price" to dbOrderFieldData.sBoxPrice,
                "quantity" to dbOrderFieldData.sBoxQuantity as Int
            )
        }
        if(dbOrderFieldData.sDozenQuantity != null) {
            map["s_dozen"] = mapOf(
                "price" to dbOrderFieldData.sDozenPrice,
                "quantity" to dbOrderFieldData.sDozenQuantity as Int
            )
        }
        return map
    }

    fun getOrderStructure(recyclerView: RecyclerView) : DBOrderFieldData? {
        val xlDozenValue : Any?
        val xlBoxValue : Any?
        val lDozenValue : Any?
        val lBoxValue : Any?
        val mDozenValue : Any?
        val mBoxValue : Any?
        val sDozenValue : Any?
        val sBoxValue : Any?
        with(recyclerView.adapter as HNGridTextAdapter) {
            xlDozenValue = this.getItemWithPosition(2).response.toString().toIntOrNull()
            xlBoxValue = this.getItemWithPosition(4).response.toString().toIntOrNull()
            lDozenValue = this.getItemWithPosition(7).response.toString().toIntOrNull()
            lBoxValue = this.getItemWithPosition(9).response.toString().toIntOrNull()
            mDozenValue = this.getItemWithPosition(12).response.toString().toIntOrNull()
            mBoxValue = this.getItemWithPosition(14).response.toString().toIntOrNull()
            sDozenValue = this.getItemWithPosition(17).response.toString().toIntOrNull()
            sBoxValue = this.getItemWithPosition(19).response.toString().toIntOrNull()
        }
        if ((xlDozenValue != null && xlDozenValue != 0) || (xlBoxValue != null && xlBoxValue != 0) || (lDozenValue != null && lDozenValue != 0)
            || (lBoxValue != null && lBoxValue != 0) || (mDozenValue != null && mDozenValue != 0) || (mBoxValue != null && mBoxValue != 0) ||
            (sDozenValue != null && sDozenValue != 0) || (sBoxValue != null && sBoxValue != 0)) {
            try {
                return DBOrderFieldData(
                    xlBoxPrice = null,
                    xlBoxQuantity = xlBoxValue as Int?,
                    xlDozenPrice = null,
                    xlDozenQuantity = xlDozenValue as Int?,
                    lBoxPrice = null,
                    lBoxQuantity = lBoxValue as Int?,
                    lDozenPrice = null,
                    lDozenQuantity = lDozenValue as Int?,
                    mBoxPrice = null,
                    mBoxQuantity = mBoxValue as Int?,
                    mDozenPrice = null,
                    mDozenQuantity = mDozenValue as Int?,
                    sBoxPrice = null,
                    sBoxQuantity = sBoxValue as Int?,
                    sDozenPrice = null,
                    sDozenQuantity = sDozenValue as Int?,
                )
            } catch (e: Exception) {
                return null
            }
        } else {
            return null
        }
    }

}