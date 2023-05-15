package com.example.hueverianieto.ui.views.workersresources.fragment.allworkersresources

import com.example.hueverianieto.base.BaseState

class AllWorkersResourcesViewState (
    var isLoading: Boolean = false,
    var error: Boolean = false,
    var isEmpty: Boolean = false
) : BaseState