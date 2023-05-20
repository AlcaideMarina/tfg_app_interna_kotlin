package com.example.hueverianieto.ui.components.componentstocks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentStocksBinding
import com.example.hueverianieto.domain.model.ComponentStockModel
import com.example.hueverianieto.utils.Utils

class HNComponentStocksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentStocksBinding = ComponentStocksBinding.bind(view)

    fun render(componentStockModel: ComponentStockModel) {
        this.binding.dateText.text = Utils.parseTimestampToString(
            componentStockModel.infoDate).toString()
        this.binding.addingText.text = "+ " + componentStockModel.add.toString()
        this.binding.subtractingText.text = "- " + componentStockModel.subtract.toString()
        this.binding.totalText.text = "Total: " + componentStockModel.total.toString()
    }

}
