package com.example.hueverianieto.ui.views.internalusers.fragments.internaluserdetail

import com.example.hueverianieto.base.BaseState

class InternalUserDetailViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var correct: Boolean = false,
) : BaseState