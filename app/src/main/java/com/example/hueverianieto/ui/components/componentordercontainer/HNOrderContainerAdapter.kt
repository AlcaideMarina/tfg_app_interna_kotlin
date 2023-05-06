package com.example.hueverianieto.ui.components.componentordercontainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.OrderContainerModel

class HNOrderContainerAdapter (private val orderContainerModelList: List<OrderContainerModel>) :
    RecyclerView.Adapter<HNOrderContainerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNOrderContainerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNOrderContainerViewHolder(layoutInflater.inflate(R.layout.component_order_container, parent, false))
    }

    override fun getItemCount(): Int = orderContainerModelList.size

    override fun onBindViewHolder(holder: HNOrderContainerViewHolder, position: Int) {
        holder.render(orderContainerModelList[position])
    }

}