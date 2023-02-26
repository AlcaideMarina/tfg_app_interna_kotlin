package com.example.hueverianieto.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentButtonBinding
import com.example.hueverianieto.utils.Conversions

class HNButton : ConstraintLayout, BaseComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    private var binding: ComponentButtonBinding = ComponentButtonBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_button, this, true)
    )

    init {
        // TODO: sin implementar
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.HNButton, 0, 0
        )
        this.initBoolAttrs(typedArray)
        this.initStringAttrs(typedArray)
        typedArray.recycle()
    }

    private fun initBoolAttrs(typedArray: TypedArray) {
        typedArray.getBoolean(R.styleable.HNButton_isButtonEnabled, true).let {
            this.setIsEnabled(it)
        }
    }

    private fun initStringAttrs(typedArray: TypedArray) {
        typedArray.getString(R.styleable.HNButton_text)?.let {
            this.setText(it)
        }
    }

    fun setText(text: String) {
        this.binding.buttonText.text = text
    }

    fun setButtonTextSize(size: Float) {
        this.binding.buttonText.textSize
    }

    fun setTextBold(isBold: Boolean) {
        if (isBold) {
            this.binding.buttonText.setTypeface(null, Typeface.BOLD)
        }
    }

    fun setTextColor(color: Int) {
        this.binding.buttonText.setTextColor(color)
    }

    // TODO: revisar esta función - ¿hace lo que tiene que hacer?
    fun setHorizontalTextMargin(margin: Int) {
        val param = this.binding.buttonText.layoutParams as MarginLayoutParams
        param.setMargins(margin, 0, margin, 0)
        this.binding.buttonText.layoutParams = param
    }

    fun setIsEnabled(isEnabled: Boolean) {
        // TODO: sin implementar - ¿cómo setteamos esto?
    }

    fun setSize(width: Int?, height: Int?) {
        this.binding.buttonBackground.layoutParams =
            LayoutParams(
                if (width == null) LayoutParams.MATCH_PARENT else Conversions.convertPxToDp(width),
                if (height == null) Conversions.convertPxToDp(R.dimen.size64) else Conversions.convertPxToDp(
                    height
                )
            )
    }

    override fun getComponentContext(): Context {
        return this.context
    }
}