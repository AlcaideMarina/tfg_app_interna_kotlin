package com.example.hueverianieto.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.data.components.TextInputLayoutModel
import com.example.hueverianieto.databinding.ComponentRowTextInputLayoutBinding

open class HNRowTextInputLayout: HNTextInputLayout, BaseComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private var binding: ComponentRowTextInputLayoutBinding = ComponentRowTextInputLayoutBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_row_text_input_layout, this, true)
    )

    init {
        this.addView(this.binding.root)
    }

    fun setLeftTextInputLayout(leftInputLayoutModel: TextInputLayoutModel) {
        //this.binding.leftTextInputLayout.let
    }

}