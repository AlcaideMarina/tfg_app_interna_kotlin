package com.example.hueverianieto.ui.components.componentdailyfinalproductcontrol

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentDailyFinalProductControlBinding

class HNComponentDailyFinalProductControl : ConstraintLayout, BaseComponent {

    private var binding: ComponentDailyFinalProductControlBinding =
        ComponentDailyFinalProductControlBinding.inflate(
            LayoutInflater.from(this.context)
        )

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        this.addView(this.binding.root)
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

}
