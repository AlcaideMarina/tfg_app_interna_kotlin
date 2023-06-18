package com.example.hueverianieto.ui.components.componentgridview

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.models.local.GridTextItemData
import com.example.hueverianieto.databinding.ComponentGridTextBinding

class HNGridTextViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private val binding: ComponentGridTextBinding = ComponentGridTextBinding.bind(view)

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
            if (gridTextItemModel.response == null || gridTextItemModel.response == "") {
                gridTextItemModel.response =
                    this.binding.textInputLayoutGrid.text.toString()
            } else {
                this.binding.textInputLayoutGrid.setText(
                    gridTextItemModel.response.toString()
                )
            }
            this.binding.textInputLayoutGrid.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {}
                override fun beforeTextChanged(
                    s: CharSequence, start: Int,
                    count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence, start: Int,
                    before: Int, count: Int
                ) {
                    gridTextItemModel.response = s.toString()
                }
            })
        }
    }

}
