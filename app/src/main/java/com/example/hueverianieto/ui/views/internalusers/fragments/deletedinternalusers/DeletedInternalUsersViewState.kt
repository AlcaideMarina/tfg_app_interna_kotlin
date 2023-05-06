package com.example.hueverianieto.ui.views.internalusers.fragments.deletedinternalusers

import com.example.hueverianieto.base.BaseState

class DeletedInternalUsersViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState