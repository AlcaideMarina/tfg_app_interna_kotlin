package com.example.hueverianieto.ui.views.clients.fragments.deletedclients

import com.example.hueverianieto.base.BaseState

class DeletedClientsViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState