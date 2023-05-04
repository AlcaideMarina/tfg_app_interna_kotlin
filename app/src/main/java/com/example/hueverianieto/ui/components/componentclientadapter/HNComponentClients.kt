package com.example.hueverianieto.ui.components.componentclientadapter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentClientBinding

class HNComponentClients: ConstraintLayout, BaseComponent {

    private var binding: ComponentClientBinding = ComponentClientBinding.inflate(
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
        this.addView(this.binding.root, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    override fun getComponentContext(): Context {
        return context
    }

    override fun getView(): View {
        return this
    }

    fun setIdText(idText: String) {
        this.binding.idText.text = idText
    }

    fun setNameText(nameText: String) {
        this.binding.nameText.text = nameText
    }

    fun setCifText(cifText: String) {
        this.binding.cifText.text = cifText
    }

    fun setActualOrderText(actualOrderText: String) {
        this.binding.actualOrderText.text = actualOrderText
    }
}