package com.example.hueverianieto.data.bbdd

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class UserData(
    var bankAccount: String,
    var city: String,
    var createdBy: String,
    var deleted: Boolean,
    var direction: String,
    var dni: String,
    var email: String,
    var id: String,
    var name: String,
    var phone: Long,
    var position: Long,
    var postalCode: Long,
    var province: String,
    var ssNumber: Long,
    var surname: String,
    var uid: String,
    var user: String,
    var documentId: String?
) : Parcelable