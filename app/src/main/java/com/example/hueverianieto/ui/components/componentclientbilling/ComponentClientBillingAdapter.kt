package com.example.hueverianieto.ui.components.componentclientbilling

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.componentclientmodel.ComponentClientBillingModel

class ComponentClientBillingAdapter(
    private val componentClientBillingModeList: List<ComponentClientBillingModel>
) : RecyclerView.Adapter<ComponentClientBillingViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentClientBillingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentClientBillingViewHolder(
            layoutInflater.inflate(
                R.layout.component_client_billing,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = componentClientBillingModeList.size

    override fun onBindViewHolder(holder: ComponentClientBillingViewHolder, position: Int) {
        holder.render(componentClientBillingModeList[position])
    }

}
