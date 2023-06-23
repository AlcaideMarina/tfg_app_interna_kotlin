package com.example.hueverianieto.ui.views.clients.fragments.clientorders

import com.example.hueverianieto.base.BaseState

class ClientOrdersViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState
