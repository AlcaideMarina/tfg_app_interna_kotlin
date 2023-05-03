package com.example.hueverianieto.ui.components.rowtextinputlayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.model.textinputlayout.RowTextInputLayoutModel

class RowTextInputLayoutAdapter(private val componentRowTextInputLayoutModelList: List<RowTextInputLayoutModel>) : RecyclerView.Adapter<RowTextInputLayoutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowTextInputLayoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RowTextInputLayoutViewHolder(layoutInflater.inflate(R.layout.component_row_text_input_layout, parent, false))
    }

    override fun getItemCount(): Int = componentRowTextInputLayoutModelList.size

    override fun onBindViewHolder(holder: RowTextInputLayoutViewHolder, position: Int) {
        holder.render(componentRowTextInputLayoutModelList[position])
    }

}