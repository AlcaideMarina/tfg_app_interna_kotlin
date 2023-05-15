package com.example.hueverianieto.ui.components.componentticket

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentTicketBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.utils.Utils

class HNComponentTicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentTicketBinding = ComponentTicketBinding.bind(view)

    fun render(componentTicketModel: ComponentTicketModel) {
        this.binding.dateText.text = Utils.parseTimestampToString(componentTicketModel.expenseDate)
        this.binding.quantityText.text = componentTicketModel.quantity
        this.binding.unitsText.text = componentTicketModel.units
        this.binding.priceText.text = componentTicketModel.price.toString() + " €"
        this.binding.baseComponentContainer.setOnClickListener(
            componentTicketModel.onClickListener
        )
    }

}
