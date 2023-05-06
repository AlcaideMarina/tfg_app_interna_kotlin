package com.example.hueverianieto.ui.components.componentgridview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.GridTextItemData

class HNGridTextAdapter(
    private val gridItemModelList: List<GridTextItemData>
) : RecyclerView.Adapter<HNGridTextViewHolder>() {

    val list = gridItemModelList

    override fun getItemCount(): Int = gridItemModelList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNGridTextViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNGridTextViewHolder(layoutInflater.inflate(R.layout.component_grid_text, parent, false))
    }

    override fun onBindViewHolder(holder: HNGridTextViewHolder, position: Int) {
        holder.render(gridItemModelList[position])
    }

    fun getItemWithPosition(position: Int) : GridTextItemData {
        return this.gridItemModelList[position]
    }
}