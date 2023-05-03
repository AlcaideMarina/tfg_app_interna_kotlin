package com.example.hueverianieto.components.rowtextinputlayout

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.components.RowTextInputLayoutModel
import com.example.hueverianieto.data.components.TextInputLayoutModel
import com.example.hueverianieto.databinding.ComponentRowTextInputLayoutBinding

class RowTextInputLayoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding: ComponentRowTextInputLayoutBinding = ComponentRowTextInputLayoutBinding.bind(view)

    fun render(rowTextInputLayoutModel: RowTextInputLayoutModel) {

        renderLeftColumn(rowTextInputLayoutModel.leftTextInputLayoutModel)
        if (rowTextInputLayoutModel.rightTextInputLayoutModel != null) {
            renderRightColumn(rowTextInputLayoutModel.rightTextInputLayoutModel!!)
        } else {
            this.binding.rightTextInputLayout.visibility = View.GONE
        }

    }

    private fun renderLeftColumn(leftTextInputLayoutModel: TextInputLayoutModel) {
        leftTextInputLayoutModel.inputText?.let { inputText ->
            this.binding.leftTextInputLayout.setInputText(inputText)
        }
        leftTextInputLayoutModel.inputType?.let {inputType ->
            this.binding.leftTextInputLayout.setInputType(inputType)
        }
        leftTextInputLayoutModel.transformationMethod?.let {transformationMethod ->
            this.binding.leftTextInputLayout.setTransformationMethod(transformationMethod)
        }
        leftTextInputLayoutModel.maxLines?.let {maxLines ->
            this.binding.leftTextInputLayout.setMaxLines(maxLines)
        }
        if (leftTextInputLayoutModel.hintText != null) {
            val text = leftTextInputLayoutModel.hintText ?: ""
            this.binding.leftTextInputLayout.setHintText(text)
            this.binding.leftTextInputLayout.isHintEnabled(true)
        } else {
            this.binding.leftTextInputLayout.isHintEnabled(false)
        }
        leftTextInputLayoutModel.radius?.let { radius ->
            this.binding.leftTextInputLayout.setBoxCornerRadii(radius, radius, radius, radius)
        }
        leftTextInputLayoutModel.strokeColor?.let {strokeColor ->
            this.binding.leftTextInputLayout.setStrokeColor(strokeColor)
        }
    }

    private fun renderRightColumn(rightTextInputLayoutModel: TextInputLayoutModel) {
        rightTextInputLayoutModel.inputText?.let { inputText ->
            this.binding.rightTextInputLayout.setInputText(inputText)
        }
        rightTextInputLayoutModel.inputType?.let {inputType ->
            this.binding.rightTextInputLayout.setInputType(inputType)
        }
        rightTextInputLayoutModel.transformationMethod?.let {transformationMethod ->
            this.binding.rightTextInputLayout.setTransformationMethod(transformationMethod)
        }
        rightTextInputLayoutModel.maxLines?.let {maxLines ->
            this.binding.rightTextInputLayout.setMaxLines(maxLines)
        }
        if (rightTextInputLayoutModel.hintText != null) {
            val hint = rightTextInputLayoutModel.hintText ?: ""
            this.binding.rightTextInputLayout.setHintText(hint)
            this.binding.rightTextInputLayout.isHintEnabled(true)
        } else {
            this.binding.rightTextInputLayout.isHintEnabled(false)
        }
        rightTextInputLayoutModel.radius?.let { radius ->
            this.binding.rightTextInputLayout.setBoxCornerRadii(radius, radius, radius, radius)
        }
        rightTextInputLayoutModel.strokeColor?.let {strokeColor ->
            this.binding.rightTextInputLayout.setStrokeColor(strokeColor)
        }
    }

}