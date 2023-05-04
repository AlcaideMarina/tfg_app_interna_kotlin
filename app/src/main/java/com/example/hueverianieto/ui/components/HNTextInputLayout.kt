package com.example.hueverianieto.ui.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentTextInputLayoutBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

open class HNTextInputLayout : TextInputLayout, BaseComponent {

    private var isMandatory: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private var binding: ComponentTextInputLayoutBinding = ComponentTextInputLayoutBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_text_input_layout, this, true)
    )

    /// Text Input Layout ///
    fun setHintText(hint: String) {
        this.binding.textInputLayout.hint = hint
    }

    fun isHintEnabled(hint: Boolean) {
        this.binding.textInputLayout.isHintEnabled = hint
    }

    fun setRadius(radius: Float) {
        this.binding.textInputLayout.setBoxCornerRadii(radius, radius, radius, radius)
    }

    fun setStrokeColor(color: Int) {
        this.binding.textInputLayout.boxStrokeColor = color
    }

    fun setIconMode(icon: Int) {
        this.binding.textInputLayout.endIconMode = icon
    }

    /// Text Input Edit Text ///
    fun setInputText(text: String) {
        this.binding.textInputEditText.setText(text)
    }

    fun setInputType(inputText: Int) {
        this.binding.textInputEditText.inputType = inputText
    }

    fun setTransformationMethod(transformationMethod: TransformationMethod) {
        this.binding.textInputEditText.transformationMethod = transformationMethod
    }

    fun setMaxLines(maxLines: Int) {
        this.binding.textInputEditText.maxLines = maxLines
    }

    fun getText(): String {
        return this.binding.textInputEditText.text.toString()
    }

    fun getTextInputEditTextComponent(): TextInputEditText {
        return this.binding.textInputEditText;
    }

    /// Context
    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

}
