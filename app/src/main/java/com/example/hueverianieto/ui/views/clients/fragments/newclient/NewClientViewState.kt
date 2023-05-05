package com.example.hueverianieto.ui.views.clients.fragments.newclient

import com.example.hueverianieto.base.BaseState

class NewClientViewState (
    val error: Boolean = false,
    val isLoading: Boolean = false,
    val correct: Boolean = false,
    val popUpCode: Int? = null
) : BaseState