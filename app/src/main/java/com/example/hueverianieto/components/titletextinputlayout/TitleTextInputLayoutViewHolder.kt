package com.example.hueverianieto.components.titletextinputlayout

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.components.rowtextinputlayout.RowTextInputLayoutAdapter
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel
import com.example.hueverianieto.databinding.ComponentTitleTextInputLayoutBinding

class TitleTextInputLayoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentTitleTextInputLayoutBinding = ComponentTitleTextInputLayoutBinding.bind(view)

    fun render(titleTextInputLayoutModel: TitleTextInputLayoutModel) {
        this.binding.textView.text = titleTextInputLayoutModel.title
        this.binding.rowTextInputLayoutRecyclerView.layoutManager = LinearLayoutManager(this.binding.rowTextInputLayoutRecyclerView.context)
        this.binding.rowTextInputLayoutRecyclerView.adapter = RowTextInputLayoutAdapter(titleTextInputLayoutModel.rowTextInputLayoutModelList)
    }

}