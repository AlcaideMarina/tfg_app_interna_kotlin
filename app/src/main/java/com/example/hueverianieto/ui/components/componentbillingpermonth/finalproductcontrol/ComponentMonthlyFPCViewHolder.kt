package com.example.hueverianieto.ui.components.componentbillingpermonth.finalproductcontrol

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentBillingPerMonthContainerBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCContainerItemModel
import com.example.hueverianieto.utils.Utils
import com.example.hueverianieto.utils.Utils.capitalizeFirstChar

class ComponentMonthlyFPCViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentBillingPerMonthContainerBinding =
        ComponentBillingPerMonthContainerBinding.bind(view)

    fun render(fpcContainerItemModel: FPCContainerItemModel) {
        this.binding.billingDateText.text = Utils.parseDateToString(
            fpcContainerItemModel.monthlyFPCContainerModel.initDate.toDate(),
            "MMMM, yyyy"
        ).capitalizeFirstChar()
        this.binding.baseComponentBillingContainer.setOnClickListener(
            fpcContainerItemModel.onClickListener
        )
    }

}