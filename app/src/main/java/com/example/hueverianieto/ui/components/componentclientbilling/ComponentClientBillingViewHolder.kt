package com.example.hueverianieto.ui.components.componentclientbilling

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentClientBillingBinding
import com.example.hueverianieto.domain.model.componentclientmodel.ComponentClientBillingModel

class ComponentClientBillingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentClientBillingBinding = ComponentClientBillingBinding.bind(view)

    fun render(componentClientBillingModel: ComponentClientBillingModel) {
        this.binding.idText.text = componentClientBillingModel.id
        this.binding.nameText.text = componentClientBillingModel.company
        this.itemView.setOnClickListener(componentClientBillingModel.onClickListener)
    }

}
