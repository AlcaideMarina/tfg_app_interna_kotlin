package com.example.hueverianieto.components.componentclientadapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.components.ComponentClientModel
import com.example.hueverianieto.databinding.ComponentClientBinding

class ComponentClientViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentClientBinding = ComponentClientBinding.bind(view)

    fun render(componentClientModel: ComponentClientModel) {
        this.binding.idText.text = componentClientModel.id
        this.binding.cifText.text = "CIF: ${componentClientModel.cif}"
        this.binding.actualOrderText.text = "Pedido actual: TODO"
        this.binding.nameText.text = componentClientModel.company
        this.itemView.setOnClickListener(componentClientModel.onClickListener)
    }

}
