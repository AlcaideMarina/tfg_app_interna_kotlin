package com.example.hueverianieto.data.bbdd

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClientData(
    var cif: String,
    var city: String,
    var createdBy: String,
    var creationDatetime: Timestamp,
    var company: String,
    var direction: String,
    var email: String,
    var emailAccount: String?,
    var hasAccount: Boolean,
    var id: String,
    var phone: Long,
    var postalCode: Long,
    var province: String,
    var uid: String?,
    var user: String?,
) : Parcelable
