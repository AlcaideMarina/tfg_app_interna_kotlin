package com.example.hueverianieto.ui.views.allorders.fragments.modifyorder

import com.example.hueverianieto.base.BaseState

class ModifyOrderViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var correct: Boolean = false,
) : BaseState