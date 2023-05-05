package com.example.hueverianieto.ui.views.internalusers.fragments.newinternaluser

import com.example.hueverianieto.base.BaseState

class NewInternalUserViewState (
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val correct: Boolean = false,
    val popUpCode: Int? = null
) : BaseState