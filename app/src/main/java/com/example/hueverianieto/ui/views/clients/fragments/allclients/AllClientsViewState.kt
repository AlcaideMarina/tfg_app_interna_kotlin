package com.example.hueverianieto.ui.views.clients.fragments.allclients

import com.example.hueverianieto.base.BaseState

class AllClientsViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState
