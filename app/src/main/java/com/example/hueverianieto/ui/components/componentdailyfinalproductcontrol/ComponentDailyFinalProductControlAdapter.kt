package com.example.hueverianieto.ui.components.componentdailyfinalproductcontrol

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCDailyContainerItemModel

class ComponentDailyFinalProductControlAdapter(
    private val fpcDailyContainerItemModelList: List<FPCDailyContainerItemModel>
) : RecyclerView.Adapter<ComponentDailyFinalProductControlViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentDailyFinalProductControlViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentDailyFinalProductControlViewHolder(
            layoutInflater.inflate(
                R.layout.component_daily_final_product_control, parent, false
            )
        )
    }

    override fun getItemCount(): Int = fpcDailyContainerItemModelList.size

    override fun onBindViewHolder(
        holder: ComponentDailyFinalProductControlViewHolder,
        position: Int
    ) {
        holder.render(fpcDailyContainerItemModelList[position])
    }

}
