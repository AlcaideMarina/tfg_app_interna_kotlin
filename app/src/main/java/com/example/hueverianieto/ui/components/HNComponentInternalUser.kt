package com.example.hueverianieto.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentInternalUserBinding

class HNComponentInternalUser : ConstraintLayout, BaseComponent {

    private var binding: ComponentInternalUserBinding = ComponentInternalUserBinding.inflate(
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

    fun setDniText(cifText: String) {
        this.binding.dniText.text = cifText
    }

    fun setJobRoleText(actualOrderText: String) {
        this.binding.jobRoleText.text = actualOrderText
    }

}
