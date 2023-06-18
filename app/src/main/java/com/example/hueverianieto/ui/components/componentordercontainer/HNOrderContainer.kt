package com.example.hueverianieto.ui.components.componentordercontainer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentOrderContainerBinding

class HNOrderContainer : ConstraintLayout, BaseComponent {

    private var binding: ComponentOrderContainerBinding = ComponentOrderContainerBinding.inflate(
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

    fun setDateText(dateText: String) {
        this.binding.dateText.text = dateText
    }

    fun setOrderIdText(orderIdText: String) {
        this.binding.orderIdText.text = orderIdText
    }

    fun setCompanyText(companyText: String) {
        this.binding.companyText.text = companyText
    }

    fun setOrderSummaryText(orderSummaryText: String) {
        this.binding.orderSummaryText.text = orderSummaryText
    }

    fun setPriceText(priceText: String) {
        this.binding.priceText.text = priceText
    }

    fun setStatusDateText(statusDateText: String) {
        this.binding.statusDateText.text = statusDateText
    }

}
