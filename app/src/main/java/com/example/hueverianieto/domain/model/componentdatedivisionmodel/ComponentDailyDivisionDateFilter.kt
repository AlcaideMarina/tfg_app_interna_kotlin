package com.example.hueverianieto.domain.model.componentdatedivisionmodel

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComponentDailyDivisionDateFilter(
    val situationDatetime: Timestamp
) : Parcelable
