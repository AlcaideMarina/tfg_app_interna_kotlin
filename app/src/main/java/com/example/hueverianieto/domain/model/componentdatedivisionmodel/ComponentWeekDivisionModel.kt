package com.example.hueverianieto.domain.model.componentdatedivisionmodel

import android.view.View.OnClickListener
import com.google.firebase.Timestamp

data class ComponentWeekDivisionModel (
    var dateFilter: ComponentWeekDivisionDateFilter,
    val onClickListener: OnClickListener
)