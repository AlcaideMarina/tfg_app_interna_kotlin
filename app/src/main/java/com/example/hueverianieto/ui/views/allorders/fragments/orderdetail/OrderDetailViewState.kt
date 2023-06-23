package com.example.hueverianieto.ui.views.allorders.fragments.orderdetail

import com.example.hueverianieto.base.BaseState

class OrderDetailViewState(
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var correct: Boolean = false,
) : BaseState
