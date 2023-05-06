package com.example.hueverianieto.ui.components.componentgridview

import android.text.InputType
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.models.local.GridTextItemData
import com.example.hueverianieto.databinding.ComponentGridTextBinding

class HNGridTextViewHolder (view: View) :
    RecyclerView.ViewHolder(view) {

    private val binding : ComponentGridTextBinding = ComponentGridTextBinding.bind(view)

    fun render(gridTextItemModel: GridTextItemData) {
        if (gridTextItemModel.isTextView) {
            if (gridTextItemModel.isTextLeft) {
                this.binding.leftTextViewGrid.visibility = View.VISIBLE
                this.binding.rightTextViewGrid.visibility = View.GONE
                this.binding.leftTextViewGrid.text = gridTextItemModel.text
            } else {
                this.binding.leftTextViewGrid.visibility = View.GONE
                this.binding.rightTextViewGrid.visibility = View.VISIBLE
                this.binding.rightTextViewGrid.text = gridTextItemModel.text
            }
            this.binding.textInputLayoutGrid.visibility = View.GONE
        } else {
            this.binding.leftTextViewGrid.visibility = View.GONE
            this.binding.rightTextViewGrid.visibility = View.GONE
            this.binding.textInputLayoutGrid.visibility = View.VISIBLE
            this.binding.textInputLayoutGrid.inputType = InputType.TYPE_CLASS_NUMBER
            this.binding.textInputLayoutGrid.isEnabled = gridTextItemModel.isEnabled
            this.binding.textInputLayoutGrid.isEnabled = gridTextItemModel.isEnabled
            if(gridTextItemModel.response == null || gridTextItemModel.response == "") {
                gridTextItemModel.response =
                    this.binding.textInputLayoutGrid.text.toString()
            } else {
                this.binding.textInputLayoutGrid.setText(
                    gridTextItemModel.response.toString())
            }
        }
    }
}
