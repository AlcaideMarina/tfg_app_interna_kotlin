package com.example.hueverianieto.ui.components.componentweekdivision

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentWeekDivisionBinding
import com.example.hueverianieto.domain.model.ComponentWeekDivisionModel
import com.example.hueverianieto.utils.Utils

class ComponentWeekDivisionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentWeekDivisionBinding = ComponentWeekDivisionBinding.bind(view)

    fun render(componentWeekDivisionModel: ComponentWeekDivisionModel) {
        this.binding.filterText.text =
            Utils.parseTimestampToString(componentWeekDivisionModel.initTimestamp) + " - " +
                    Utils.parseTimestampToString(componentWeekDivisionModel.endTimestamp)
        this.binding.baseComponentContainer.setOnClickListener(
            componentWeekDivisionModel.onClickListener)
    }

}
