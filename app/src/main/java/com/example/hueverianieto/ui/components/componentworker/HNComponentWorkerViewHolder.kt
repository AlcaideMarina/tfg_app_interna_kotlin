package com.example.hueverianieto.ui.components.componentworker

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.databinding.ComponentWorkerBinding
import com.example.hueverianieto.domain.model.componentworkers.ComponentWorkersModel

class HNComponentWorkerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentWorkerBinding = ComponentWorkerBinding.bind(view)

    fun render(componentWorkersModel: ComponentWorkersModel){
        this.binding.idText.text = componentWorkersModel.id
        this.binding.nameText.text = componentWorkersModel.name + " " + componentWorkersModel.surname
        this.binding.salaryText.text = (componentWorkersModel.salary ?: "-").toString()  + " €"
        this.binding.baseComponentOrderContainer.setOnClickListener(
            componentWorkersModel.onClickListener )
    }

}
