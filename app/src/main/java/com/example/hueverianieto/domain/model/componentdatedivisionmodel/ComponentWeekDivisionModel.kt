package com.example.hueverianieto.domain.model.componentdatedivisionmodel

import android.view.View.OnClickListener

data class ComponentWeekDivisionModel(
    var dateFilter: ComponentWeekDivisionDateFilter,
    val onClickListener: OnClickListener
)
