package com.example.hueverianieto.ui.components.componentgridview

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CustomGridLayoutManager(context: Context?, spanCount: Int)
    : GridLayoutManager(context, spanCount) {

    private var isVerticalScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        isVerticalScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isVerticalScrollEnabled && super.canScrollVertically()
    }

}
