package com.example.hueverianieto.components.componentinternaluseradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.components.ComponentInternalUserModel

class ComponentInternalUserAdapter(
    private val componentInternalUserModelList: List<ComponentInternalUserModel>
) : RecyclerView.Adapter<ComponentInternalUserViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentInternalUserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComponentInternalUserViewHolder(layoutInflater.inflate(R.layout.component_internal_user, parent, false))
    }

    override fun getItemCount(): Int = componentInternalUserModelList.size

    override fun onBindViewHolder(holder: ComponentInternalUserViewHolder, position: Int) {
        holder.render(componentInternalUserModelList[position])
    }

}
