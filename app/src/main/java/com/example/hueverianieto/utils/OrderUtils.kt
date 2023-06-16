package com.example.hueverianieto.utils

import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.models.local.DBOrderFieldData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.local.GridTextItemData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.google.firebase.Timestamp
import java.lang.Math.round
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

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

    fun getOrderDataModifyGridModel(orderData: OrderData, eggPricesData: EggPricesData, isEnable: Boolean) : List<GridTextItemData> {

        val dbOrderModel = orderDataToBDOrderModel(orderData)

        return listOf(
            GridTextItemData(0,
                true, "XL"
            ),
            GridTextItemData(1,
                true, "Docena:"
            ),
            GridTextItemData(2,
                false, null, isEnable, response = dbOrderModel.xlDozenQuantity.toString()
            ),
            GridTextItemData(3,
                true, (dbOrderModel.xlDozenPrice ?: eggPricesData.xlDozen).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(4,
                true, "Caja:"
            ),
            GridTextItemData(5,
                false, null, isEnable, response = dbOrderModel.xlBoxQuantity.toString()
            ),
            GridTextItemData(6,
                true, (dbOrderModel.xlBoxPrice ?: eggPricesData.xlBox).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(7,
                true, "L"
            ),
            GridTextItemData(8,
                true, "Docena:"
            ),
            GridTextItemData(9,
                false, null, isEnable, response = dbOrderModel.lDozenQuantity.toString()
            ),
            GridTextItemData(10,
                true, (dbOrderModel.lDozenPrice ?: eggPricesData.lDozen).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(11,
                true, "Caja:"
            ),
            GridTextItemData(12,
                false, null, isEnable, response = dbOrderModel.lBoxQuantity.toString()
            ),
            GridTextItemData(13,
                true, (dbOrderModel.lBoxPrice ?: eggPricesData.lBox).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(14,
                true, "M"
            ),
            GridTextItemData(15,
                true, "Docena:"
            ),
            GridTextItemData(16,
                false, null, isEnable, response = dbOrderModel.mDozenQuantity.toString()
            ),
            GridTextItemData(17,
                true, (dbOrderModel.mDozenPrice ?: eggPricesData.mDozen).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(18,
                true, "Caja:"
            ),
            GridTextItemData(19,
                false, null, isEnable, response = dbOrderModel.mBoxQuantity.toString()
            ),
            GridTextItemData(20,
                true, (dbOrderModel.mBoxPrice ?: eggPricesData.mBox).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(21,
                true, "S"
            ),
            GridTextItemData(22,
                true, "Docena:"
            ),
            GridTextItemData(23,
                false, null, isEnable, response = dbOrderModel.sDozenQuantity.toString()
            ),
            GridTextItemData(24,
                true, (dbOrderModel.sDozenPrice ?: eggPricesData.sDozen).toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(25,
                true, "Caja:"
            ),
            GridTextItemData(26,
                false, null, isEnable, response = dbOrderModel.sBoxQuantity.toString()
            ),
            GridTextItemData(27,
                true, (dbOrderModel.sBoxPrice ?: eggPricesData.sBox).toString() + " €/ud", isTextLeft = false
            ),
        )
    }

    fun getNewOrderGridModel(eggPricesData: EggPricesData) : List<GridTextItemData> {
        return listOf(
            GridTextItemData(0,
                true, "XL"
            ),
            GridTextItemData(1,
                true, "Docena:"
            ),
            GridTextItemData(2,
                false, null, response = ""
            ),
            GridTextItemData(3,
                true, (eggPricesData.xlDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(4,
                true, "Caja:"
            ),
            GridTextItemData(5,
                false, null, response = ""
            ),
            GridTextItemData(6,
                true,  (eggPricesData.xlBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(7,
                true, "L"
            ),
            GridTextItemData(8,
                true, "Docena:"
            ),
            GridTextItemData(9,
                false, null, response = ""
            ),
            GridTextItemData(10,
                true, (eggPricesData.lDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(11,
                true, "Caja:"
            ),
            GridTextItemData(12,
                false, null, response = ""
            ),
            GridTextItemData(13,
                true, (eggPricesData.lBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(14,
                true, "M"
            ),
            GridTextItemData(15,
                true, "Docena:"
            ),
            GridTextItemData(16,
                false, null, response = ""
            ),
            GridTextItemData(17,
                true, (eggPricesData.mDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(18,
                true, "Caja:"
            ),
            GridTextItemData(19,
                false, null, response = ""
            ),
            GridTextItemData(20,
                true, (eggPricesData.mBox ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(21,
                true, "S"
            ),
            GridTextItemData(22,
                true, "Docena:"
            ),
            GridTextItemData(23,
                false, null, response = ""
            ),
            GridTextItemData(24,
                true, (eggPricesData.sDozen ?: "-").toString() + " €/ud", isTextLeft = false
            ),
            GridTextItemData(25,
                true, "Caja:"
            ),
            GridTextItemData(26,
                false, null, response = ""
            ),
            GridTextItemData(27,
                true, (eggPricesData.sBox ?: "-").toString() + " €/ud", isTextLeft = false
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
        val xlDozenPrice : Any?
        val xlBoxValue : Any?
        val xlBoxPrice : Any?
        val lDozenValue : Any?
        val lDozenPrice : Any?
        val lBoxValue : Any?
        val lBoxPrice : Any?
        val mDozenValue : Any?
        val mDozenPrice : Any?
        val mBoxValue : Any?
        val mBoxPrice : Any?
        val sDozenValue : Any?
        val sDozenPrice : Any?
        val sBoxValue : Any?
        val sBoxPrice : Any?
        with(recyclerView.adapter as HNGridTextAdapter) {
            xlDozenValue =
                if(this.getItemWithPosition(2).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(2).response.toString().toIntOrNull()
            xlDozenPrice =
                if(this.getItemWithPosition(3).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(3).text!!.split(" ")[0].toDoubleOrNull()
            xlBoxValue =
                if(this.getItemWithPosition(5).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(5).response.toString().toIntOrNull()
            xlBoxPrice =
                if(this.getItemWithPosition(6).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(6).text!!.split(" ")[0].toDoubleOrNull()
            lDozenValue =
                if(this.getItemWithPosition(9).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(9).response.toString().toIntOrNull()
            lDozenPrice =
                if(this.getItemWithPosition(10).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(10).text!!.split(" ")[0].toDoubleOrNull()
            lBoxValue =
                if(this.getItemWithPosition(12).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(12).response.toString().toIntOrNull()
            lBoxPrice =
                if(this.getItemWithPosition(13).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(13).text!!.split(" ")[0].toDoubleOrNull()
            mDozenValue =
                if(this.getItemWithPosition(16).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(16).response.toString().toIntOrNull()
            mDozenPrice =
                if(this.getItemWithPosition(17).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(17).text!!.split(" ")[0].toDoubleOrNull()
            mBoxValue =
                if(this.getItemWithPosition(19).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(19).response.toString().toIntOrNull()
            mBoxPrice =
                if(this.getItemWithPosition(20).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(20).text!!.split(" ")[0].toDoubleOrNull()
            sDozenValue =
                if(this.getItemWithPosition(23).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(23).response.toString().toIntOrNull()
            sDozenPrice =
                if(this.getItemWithPosition(24).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(24).text!!.split(" ")[0].toDoubleOrNull()
            sBoxValue =
                if(this.getItemWithPosition(26).response.toString().toIntOrNull() == 0) null
                else this.getItemWithPosition(26).response.toString().toIntOrNull()
            sBoxPrice =
                if(this.getItemWithPosition(27).text!!.split(" ")[0].toDoubleOrNull() == 0.0) null
                else this.getItemWithPosition(27).text!!.split(" ")[0].toDoubleOrNull()
        }
        if (xlDozenValue != null || xlBoxValue != null || lDozenValue != null
            || lBoxValue != null || mDozenValue != null || mBoxValue != null ||
            sDozenValue != null || sBoxValue != null) {
            try {
                return DBOrderFieldData(
                    xlBoxPrice = xlBoxPrice as Double?,
                    xlBoxQuantity = xlBoxValue as Int?,
                    xlDozenPrice = xlDozenPrice as Double?,
                    xlDozenQuantity = xlDozenValue as Int?,
                    lBoxPrice = lBoxPrice as Double?,
                    lBoxQuantity = lBoxValue as Int?,
                    lDozenPrice = lDozenPrice as Double?,
                    lDozenQuantity = lDozenValue as Int?,
                    mBoxPrice = mBoxPrice as Double?,
                    mBoxQuantity = mBoxValue as Int?,
                    mDozenPrice = mDozenPrice as Double?,
                    mDozenQuantity = mDozenValue as Int?,
                    sBoxPrice = sBoxPrice as Double?,
                    sBoxQuantity = sBoxValue as Int?,
                    sDozenPrice = sDozenPrice as Double?,
                    sDozenQuantity = sDozenValue as Int?,
                )
            } catch (e: Exception) {
                return null
            }
        } else {
            return null
        }
    }

    fun getTotalPrice(dbOrderFieldData: DBOrderFieldData) : Double {

        var totalPrice: Double = 0.0
        with(dbOrderFieldData) {
            if (xlBoxQuantity != null && xlBoxPrice != null) {
                totalPrice += (xlBoxQuantity!! as Int) * (xlBoxPrice!! as Double)
            }
            if (xlDozenQuantity != null && xlDozenPrice != null) {
                totalPrice += (xlDozenQuantity!! as Int) * (xlDozenPrice!! as Double)
            }
            if (lBoxQuantity != null && lBoxPrice != null) {
                totalPrice += (lBoxQuantity!! as Int) * (lBoxPrice!! as Double)
            }
            if (lDozenQuantity != null && lDozenPrice != null) {
                totalPrice += (lDozenQuantity!! as Int) * (lDozenPrice!! as Double)
            }
            if (mBoxQuantity != null && mBoxPrice != null) {
                totalPrice += (mBoxQuantity!! as Int) * (mBoxPrice!! as Double)
            }
            if (mDozenQuantity != null && mDozenPrice != null) {
                totalPrice += (mDozenQuantity!! as Int) * (mDozenPrice!! as Double)
            }
            if (sBoxQuantity != null && sBoxPrice != null) {
                totalPrice += (sBoxQuantity!! as Int) * (sBoxPrice!! as Double)
            }
            if (sDozenQuantity != null && sDozenPrice != null) {
                totalPrice += (sDozenQuantity!! as Int) * (sDozenPrice!! as Double)
            }
        }

        (totalPrice * 100).roundToInt() / 100

        return ((totalPrice * 100).roundToInt()).toDouble() / 100
    }

}