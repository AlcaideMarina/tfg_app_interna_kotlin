package com.example.hueverianieto.ui.components.componentbillingpermonth.billing

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentBillingPerMonthContainerBinding
import com.example.hueverianieto.domain.model.billingcontaineritemmodel.BillingContainerItemModel
import com.example.hueverianieto.utils.Utils

class ComponentBillingPerMonthViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentBillingPerMonthContainerBinding =
        ComponentBillingPerMonthContainerBinding.bind(view)

    fun render(billingContainerItemModel: BillingContainerItemModel) {
        this.binding.billingDateText.text = Utils.parseDateToString(
            billingContainerItemModel.billingPerMonthContainerModel.initDate.toDate(),
            "MMMM, yyyy"
        )
        this.binding.baseComponentBillingContainer.setOnClickListener(
            billingContainerItemModel.onClickListener
        )
    }

}