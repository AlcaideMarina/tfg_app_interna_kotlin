package com.example.hueverianieto.ui.views.feedresources.fragments.newfeedresources

import com.example.hueverianieto.base.BaseState

class NewFeedResourcesViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState