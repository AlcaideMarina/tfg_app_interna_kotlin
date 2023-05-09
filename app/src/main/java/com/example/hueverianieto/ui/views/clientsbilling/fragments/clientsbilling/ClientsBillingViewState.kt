package com.example.hueverianieto.ui.views.clientsbilling.fragments.clientsbilling

import com.example.hueverianieto.base.BaseState

class ClientsBillingViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState