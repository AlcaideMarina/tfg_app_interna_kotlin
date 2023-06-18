package com.example.hueverianieto.ui.components.componentordercontainer

import android.icu.text.SimpleDateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.databinding.ComponentOrderContainerBinding
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils

class HNOrderContainerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentOrderContainerBinding = ComponentOrderContainerBinding.bind(view)

    fun render(orderContainerModel: OrderContainerModel) {

        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val dateStr = sdf.format(orderContainerModel.orderDate.toDate())

        //TODO: Esto no funciona
        val priceStr = if (orderContainerModel.price == (-1).toLong()) {
            "-"
        } else {
            orderContainerModel.price.toString()
        }
        // TODO: Transformar status
        var statusStr = Utils.getKey(Constants.orderStatus, orderContainerModel.status.toInt())
        if (statusStr == null) statusStr = R.string.unknown_status

        this.binding.dateText.text = dateStr
        this.binding.orderIdText.text = "ID Pedido: " + orderContainerModel.orderId.toString()
        this.binding.companyText.text = orderContainerModel.company
        this.binding.orderSummaryText.text = orderContainerModel.orderSummary
        this.binding.priceText.text = "Precio: " + priceStr + " €"
        this.binding.statusDateText.text = this.binding.root.context.getString(statusStr)

        this.binding.baseComponentOrderContainer.setOnClickListener(
            orderContainerModel.onClickListener
        )

    }

}
