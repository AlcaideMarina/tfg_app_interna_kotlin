package com.example.hueverianieto.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.base.BaseModel
import com.example.hueverianieto.databinding.ComponentContainerBorderCheckTitleBinding

class HNContainerBorderCheckTitle: LinearLayout, BaseComponent {

    private var binding: ComponentContainerBorderCheckTitleBinding = ComponentContainerBorderCheckTitleBinding.inflate(
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
        this.addView(this.binding.root, ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

    /*fun setBodyContent(layoutManager: LayoutManager, adapter: RecyclerView.Adapter<>) {
        this.binding.bodyContainerRecyclerView.layoutManager = layoutManager
        this.binding.bodyContainerRecyclerView.adapter = adapter
    }*/

    fun getBodyContainer() : RecyclerView {
        return this.binding.bodyContainerRecyclerView
    }

}