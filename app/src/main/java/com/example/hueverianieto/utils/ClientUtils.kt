package com.example.hueverianieto.utils

import android.os.Parcelable
import android.text.InputType
import com.example.hueverianieto.data.bbdd.ClientData
import com.example.hueverianieto.data.components.RowTextInputLayoutModel
import com.example.hueverianieto.data.components.TextInputLayoutModel
import com.example.hueverianieto.data.components.TitleTextInputLayoutModel
import com.google.firebase.Timestamp

// TODO: Sacar constantes

object ClientUtils {

    fun checkErrorMap(data: MutableMap<String, Any?>?) : String? {
        return if (data == null) {
            "empty input map"
        } else if (data.containsKey("cif") && data.containsKey("city") &&
            data.containsKey("created_by") && data.containsKey("creation_datetime") &&
            data.containsKey("company") && data.containsKey("direction") && data.containsKey("email")
            && data.containsKey("email_account") && data.containsKey("has_account") && data.containsKey("id") &&
            data.containsKey("phone") && data.containsKey("postal_code") &&
            data.containsKey("province") && data.containsKey("uid") &&
            data.containsKey("user")
        ) {
            null
        } else {
            "missing fields"
        }
    }

    // TODO: Investigar cómo comprobar si data["phone"] es List<Map<String, Long>>
    fun mapToParcelable(data: MutableMap<String, Any?>, documentId: String?): ClientData {
        return ClientData(
            data["cif"] as String,
            data["city"] as String,
            data["created_by"] as String,
            data["creation_datetime"] as Timestamp,
            data["company"] as String,
            data["direction"] as String,
            data["email"] as String,
            data["email_account"] as String?,
            data["has_account"] as Boolean,
            data["id"] as String,
            data["phone"] as List<Map<String, Long>>,
            data["postal_code"] as Long,
            data["province"] as String,
            data["uid"] as String?,
            data["user"] as String?,
            documentId
        )
    }

    fun newClientFields() : MutableList<TitleTextInputLayoutModel> {
        val fieldList: MutableList<TitleTextInputLayoutModel> = mutableListOf()
        fieldList.add(
            TitleTextInputLayoutModel(
                "Empresa:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Dirección:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Ciudad:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Provincia:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Código postal:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "CIF:",
                listOf(RowTextInputLayoutModel(TextInputLayoutModel()))
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Correo:",
                listOf(RowTextInputLayoutModel(
                    TextInputLayoutModel(
                        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT
                    ))
                )
            )
        )
        fieldList.add(
            TitleTextInputLayoutModel(
                "Teléfono",
                listOf(
                    RowTextInputLayoutModel(
                        TextInputLayoutModel(),
                        TextInputLayoutModel(
                            inputType = InputType.TYPE_CLASS_PHONE
                        )
                    ),
                    RowTextInputLayoutModel(
                        TextInputLayoutModel(),
                        TextInputLayoutModel(
                            inputType = InputType.TYPE_CLASS_PHONE
                        )
                    )
                )
            )
        )
        return fieldList
    }

}
