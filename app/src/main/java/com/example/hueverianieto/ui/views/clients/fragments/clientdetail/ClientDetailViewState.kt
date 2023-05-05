package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import com.example.hueverianieto.base.BaseState

class ClientDetailViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var correct: Boolean = false,
    var hasAccount : Boolean = false,
    var hasOrders : Boolean = false,
) : BaseState