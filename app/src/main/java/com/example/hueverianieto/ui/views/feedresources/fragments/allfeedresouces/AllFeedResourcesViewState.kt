package com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces

import com.example.hueverianieto.base.BaseState

class AllFeedResourcesViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState