package com.example.hueverianieto.ui.components.componentdailyfinalproductcontrol

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentDailyFinalProductControlBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCDailyContainerItemModel
import com.example.hueverianieto.utils.Utils

class ComponentDailyFinalProductControlViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentDailyFinalProductControlBinding =
        ComponentDailyFinalProductControlBinding.bind(view)

    fun render(fpcDailyContainerItemModel: FPCDailyContainerItemModel) {
        this.binding.dateText.text = Utils.parseDateToString(
            fpcDailyContainerItemModel.fpcData.issueDatetime.toDate(),
            "dd/MM"
        )
        this.binding.acceptedEggsText.text =
            fpcDailyContainerItemModel.fpcData.acceptedEggs.toString() + " aceptados"
        this.binding.rejectedEggsText.text =
            fpcDailyContainerItemModel.fpcData.rejectedEggs.toString() + " rechazados"
        if (fpcDailyContainerItemModel.onClickListener != null) {
            this.binding.baseComponentContainer.setOnClickListener(
                fpcDailyContainerItemModel.onClickListener
            )
        } else {
            this.binding.arrowIcon.visibility = View.GONE
        }

    }

}
