package com.example.hueverianieto.ui.components.componentstocks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.ComponentStockModel

class HNComponentStocksAdapter (
    private val componentStockModelList: List<ComponentStockModel>
) : RecyclerView.Adapter<HNComponentStocksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNComponentStocksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNComponentStocksViewHolder(layoutInflater.inflate(R.layout.component_stocks, parent, false))
    }

    override fun getItemCount(): Int = this.componentStockModelList.size

    override fun onBindViewHolder(holder: HNComponentStocksViewHolder, position: Int) {
        holder.render(componentStockModelList[position])
    }

}
