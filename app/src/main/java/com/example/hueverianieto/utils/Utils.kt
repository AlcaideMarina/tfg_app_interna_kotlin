package com.example.hueverianieto.utils

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.ui.components.HNModalDialog


object Utils {

    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun setPopUp(alertDialog: HNModalDialog, context: Context, title: String,
                 message: String, leftButton: String, rightButton: String?,
                 leftButtonListener: View.OnClickListener,
                 rightButtonListener: View.OnClickListener?) {
        alertDialog.show(
            context,
            ModalDialogModel(
                title,
                message,
                leftButton,
                rightButton,
                leftButtonListener,
                rightButtonListener,
                true
            )
        )
    }

    fun <K, V> getKey(map: Map<K, V>, target: V): K? {
        for ((key, value) in map) {
            if (target == value) {
                return key
            }
        }
        return null
    }

}