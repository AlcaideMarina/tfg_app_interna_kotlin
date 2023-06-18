package com.example.hueverianieto.ui.views.main.fragments.orderanddelivery

import com.example.hueverianieto.base.BaseState

class OrderAndDeliveryViewState(
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState