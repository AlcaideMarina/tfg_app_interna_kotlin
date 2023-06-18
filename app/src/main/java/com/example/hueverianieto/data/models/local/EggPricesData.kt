package com.example.hueverianieto.data.models.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class EggPricesData(
    val xlBox: Number? = null,
    val xlDozen: Number? = null,
    val lBox: Number? = null,
    val lDozen: Number? = null,
    val mBox: Number? = null,
    val mDozen: Number? = null,
    val sBox: Number? = null,
    val sDozen: Number? = null
) : Parcelable
