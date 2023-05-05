package com.example.hueverianieto.data.models.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp


@Parcelize
data class ClientData(
    var cif: String,
    var city: String,
    var createdBy: String,
    var company: String,
    var deleted: Boolean,
    var direction: String,
    var email: String,
    var hasAccount: Boolean,
    var id: Long?,
    var phone: List<Map<String, Long>>,
    var postalCode: Long,
    var province: String,
    var uid: String?,
    var user: String?,
    var documentId: String?
) : Parcelable
