package com.example.hueverianieto.ui.components.componentticket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel

class HNComponentTicketAdapter (
    private val ticketModelList: List<ComponentTicketModel>
) : RecyclerView.Adapter<HNComponentTicketViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HNComponentTicketViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HNComponentTicketViewHolder(layoutInflater.inflate(R.layout.component_ticket, parent, false))
    }

    override fun getItemCount(): Int = this.ticketModelList.size

    override fun onBindViewHolder(holder: HNComponentTicketViewHolder, position: Int) {
        holder.render(ticketModelList[position])
    }

}
