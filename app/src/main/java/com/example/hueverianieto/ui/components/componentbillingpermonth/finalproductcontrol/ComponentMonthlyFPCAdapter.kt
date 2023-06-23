package com.example.hueverianieto.ui.components.componentbillingpermonth.finalproductcontrol

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCContainerItemModel

class ComponentMonthlyFPCAdapter(
    private val fpcContainerItemModelList: List<FPCContainerItemModel>
) : RecyclerView.Adapter<ComponentMonthlyFPCViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentMonthlyFPCViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentMonthlyFPCViewHolder(
            layoutInflater.inflate(
                R.layout.component_billing_per_month_container, parent, false
            )
        )
    }

    override fun getItemCount(): Int = fpcContainerItemModelList.size

    override fun onBindViewHolder(holder: ComponentMonthlyFPCViewHolder, position: Int) {
        holder.render(fpcContainerItemModelList[position])
    }

}
