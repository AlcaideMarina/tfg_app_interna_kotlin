package com.example.hueverianieto.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentTextInputLayoutBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

open class HNTextInputLayout : TextInputLayout, BaseComponent {

    private var isMandatory: Boolean = false
    private var errorText: String? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private var binding: ComponentTextInputLayoutBinding = ComponentTextInputLayoutBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_text_input_layout, this, true)
    )

    init {
        // TODO: sin implementar - ¿la siguiente línea es necesaria?
        //orientation = VERTICAL
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.HNTextInputLayout, 0, 0
        )
        this.initBoolAttrs(typedArray)
        this.initStringAttrs(typedArray)
        typedArray.recycle()
    }

    private fun initBoolAttrs(typedArray: TypedArray) {
        this.isMandatory = typedArray.getBoolean(
            R.styleable.HNTextInputLayout_isMandatory, false
        )
        this.isEnabled = typedArray.getBoolean(
            R.styleable.HNTextInputLayout_isEnabled, true
        )
    }

    private fun initStringAttrs(typedArray: TypedArray) {
        typedArray.getString(R.styleable.HNTextInputLayout_errorText)?.let {
            this.errorText = it
            this.setErrorTextAndIcon(
                it,
                ResourcesCompat.getDrawable(
                    typedArray.resources,
                    R.drawable.ic_logo,
                    null
                )
            )
        }
        typedArray.getString(R.styleable.HNTextInputLayout_hint)?.let {
            setHintText(it)
        }
    }

    private fun setErrorTextAndIcon(text: String, drawable: Drawable?) {
        this.binding.errorText.text = text
        this.binding.errorIcon.setImageDrawable(drawable)
    }

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