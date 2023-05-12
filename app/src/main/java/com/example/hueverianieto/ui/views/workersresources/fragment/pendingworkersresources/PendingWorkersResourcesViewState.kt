package com.example.hueverianieto.ui.views.workersresources.fragment.pendingworkersresources

import com.example.hueverianieto.base.BaseState

class PendingWorkersResourcesViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState