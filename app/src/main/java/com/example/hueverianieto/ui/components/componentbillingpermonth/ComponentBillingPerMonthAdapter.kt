package com.example.hueverianieto.ui.components.componentbillingpermonth

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.billingcontaineritemmodel.BillingContainerItemModel

class ComponentBillingPerMonthAdapter (
    private val billingContainerModelItemList: List<BillingContainerItemModel>
) : RecyclerView.Adapter<ComponentBillingPerMonthViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentBillingPerMonthViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentBillingPerMonthViewHolder(layoutInflater.inflate(
            R.layout.component_billing_per_month_container, parent, false)
        )
    }

    override fun getItemCount(): Int = billingContainerModelItemList.size

    override fun onBindViewHolder(holder: ComponentBillingPerMonthViewHolder, position: Int) {
        holder.render(billingContainerModelItemList[position])
    }

}
