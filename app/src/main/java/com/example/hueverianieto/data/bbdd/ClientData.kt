package com.example.hueverianieto.data.bbdd

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClientData(
    var cif: String,
    var city: String,
    var createdBy: String,
    var uid: String?,
    var province: String,
    var emailAccount: String?,
    var hasAccount: Boolean,
    var phone: Long,
    var creationDatetime: Timestamp,
    var company: String,
    var id: String,
    var postalCode: Long,
    var user: String?,
    var email: String,
    var direction: String
) : Parcelable