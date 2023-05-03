package com.example.hueverianieto.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentOrderBinding

class HNComponentOrder: ConstraintLayout, BaseComponent {

    private var binding: ComponentOrderBinding = ComponentOrderBinding.inflate(
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
        TODO("Not yet implemented")
    }

    override fun getView(): View {
        TODO("Not yet implemented")
    }

    fun setDateText(idText: String) {
        this.binding.dateText.text = idText
    }

    fun setOrderDeliveryText(deliveryText: String) {
        this.binding.orderDeliveryText.text = deliveryText
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

    fun setDeliveryInfoText(deliveryInfoText: String) {
        this.binding.deliveryInfoText.text = deliveryInfoText
    }

}