package com.example.hueverianieto.components.componentclientadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.components.ComponentClientModel

class ComponentClientAdapter(private val componentClientModelList: List<ComponentClientModel>) : RecyclerView.Adapter<ComponentClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentClientViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentClientViewHolder(layoutInflater.inflate(R.layout.component_client, parent, false))
    }

    override fun getItemCount(): Int = componentClientModelList.size

    override fun onBindViewHolder(holder: ComponentClientViewHolder, position: Int) {
        holder.render(componentClientModelList[position])
    }
    
}
