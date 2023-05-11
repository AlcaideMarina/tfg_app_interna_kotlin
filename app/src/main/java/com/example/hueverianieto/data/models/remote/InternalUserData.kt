package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InternalUserData(
    var bankAccount: String,
    var city: String,
    var createdBy: String,
    var deleted: Boolean,
    var direction: String,
    var dni: String,
    var email: String,
    var id: Long?,
    var name: String,
    var phone: Long,
    var position: Long,
    var postalCode: Long,
    var province: String,
    var salary: Long?,
    var ssNumber: Long,
    var surname: String,
    var uid: String?,
    var user: String,
    var documentId: String?
) : Parcelable