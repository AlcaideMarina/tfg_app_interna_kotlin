package com.example.hueverianieto.ui.components.componentweekdivision

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.componentdatedivisionmodel.ComponentWeekDivisionModel

class ComponentWeekDivisionAdapter(
    private val componentWeekDivisionModelList: List<ComponentWeekDivisionModel>
) : RecyclerView.Adapter<ComponentWeekDivisionViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentWeekDivisionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentWeekDivisionViewHolder(
            layoutInflater.inflate(R.layout.component_week_division, parent, false))
    }

    override fun getItemCount(): Int = this.componentWeekDivisionModelList.size

    override fun onBindViewHolder(holder: ComponentWeekDivisionViewHolder, position: Int) {
        holder.render(componentWeekDivisionModelList[position])
    }

}
