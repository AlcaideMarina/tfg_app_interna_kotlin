package com.example.hueverianieto.ui.components.componentordercontainer.componentordercontainertopthree

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.ui.components.componentordercontainer.HNOrderContainerViewHolder

class HNOrderContainerTopThreeContainerAdapter (
    private val orderContainerModelList: List<OrderContainerModel>
) : RecyclerView.Adapter<HNOrderContainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNOrderContainerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNOrderContainerViewHolder(layoutInflater.inflate(R.layout.component_order_container, parent, false))
    }

    override fun getItemCount(): Int {
        return if (orderContainerModelList.size >= 3) 3 else orderContainerModelList.size
    }

    override fun onBindViewHolder(holder: HNOrderContainerViewHolder, position: Int) {
        holder.render(orderContainerModelList[position])
    }

}
