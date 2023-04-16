package com.example.hueverianieto.components.titletextinputlayout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.R
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel

// TODO: La arq se queda terminada - hay que hacer el recycler view en el fragment y meterle este adapter
class TitleTextInputLayoutAdapter(private val titleTextInputLayoutModelList: List<TitleTextInputLayoutModel>): RecyclerView.Adapter<TitleTextInputLayoutViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TitleTextInputLayoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TitleTextInputLayoutViewHolder(layoutInflater.inflate(R.layout.component_title_text_input_layout, parent, false))
    }

    override fun getItemCount(): Int = titleTextInputLayoutModelList.size

    override fun onBindViewHolder(holder: TitleTextInputLayoutViewHolder, position: Int) {
        holder.render(titleTextInputLayoutModelList[position])
    }

}