package com.example.hueverianieto.base

import android.content.Context
import android.view.View

interface BaseComponent {
    fun getComponentContext(): Context
    fun getView(): View
}