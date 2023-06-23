package com.example.hueverianieto.ui.views.allorders.fragments.allorders

import com.example.hueverianieto.base.BaseState

class AllOrdersViewState(
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState
