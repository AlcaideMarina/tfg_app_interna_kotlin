package com.example.hueverianieto.ui.components.componentweekdivision

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentWeekDivisionBinding

class HNComponentWeekDivision : ConstraintLayout, BaseComponent {

    private val binding: ComponentWeekDivisionBinding = ComponentWeekDivisionBinding.inflate(
        LayoutInflater.from(this.context)
    )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

}
