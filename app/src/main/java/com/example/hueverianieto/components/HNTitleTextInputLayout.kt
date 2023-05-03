package com.example.hueverianieto.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.components.rowtextinputlayout.RowTextInputLayoutAdapter
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel
import com.example.hueverianieto.databinding.ComponentTitleTextInputLayoutBinding

class HNTitleTextInputLayout: HNRowTextInputLayout, BaseComponent {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private var binding: ComponentTitleTextInputLayoutBinding = ComponentTitleTextInputLayoutBinding.bind(
        LayoutInflater
            .from(context)
            .inflate(R.layout.component_title_text_input_layout, this, true)
    )

    init {
        this.addView(this.binding.root)
    }

    fun setUp(model: TitleTextInputLayoutModel) {
        this.binding.textView.text = model.title
        this.binding.rowTextInputLayoutRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.rowTextInputLayoutRecyclerView.adapter = RowTextInputLayoutAdapter(model.rowTextInputLayoutModelList)
    }

}