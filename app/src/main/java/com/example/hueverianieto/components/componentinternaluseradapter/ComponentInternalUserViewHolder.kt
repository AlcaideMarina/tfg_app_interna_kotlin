package com.example.hueverianieto.components.componentinternaluseradapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.components.ComponentInternalUserModel
import com.example.hueverianieto.databinding.ComponentInternalUserBinding

class ComponentInternalUserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentInternalUserBinding = ComponentInternalUserBinding.bind(view)

    fun render(componentInternalUserModel: ComponentInternalUserModel) {
        this.binding.idText.text = componentInternalUserModel.id
        this.binding.nameText.text = "${componentInternalUserModel.name} ${componentInternalUserModel.surname}"
        this.binding.dniText.text = "DNI: ${componentInternalUserModel.dni}"
        this.binding.jobRoleText.text = "Puesto: ${componentInternalUserModel.jobRole}"
    }

}
