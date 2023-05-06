package com.example.hueverianieto.utils

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.ui.components.HNModalDialog
import com.google.firebase.Timestamp
import java.util.*


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

    fun parseTimestampToString(timestamp: Timestamp?) : String? {
        return if(timestamp == null) {
            null
        } else {
            SimpleDateFormat(Constants.dateFormat).format(timestamp.toDate())
        }
    }

    fun parseStringToTimestamp(dateStr : String, pattern : String? = Constants.dateFormat) :
            Timestamp = Timestamp(SimpleDateFormat(pattern).parse(dateStr))

    fun addToDate(date: Date, daysToAdd: Int = 0, monthsToAdd: Int = 0) : Date {
        val c = Calendar.getInstance()
        c.time = date
        c.add(Calendar.DATE, daysToAdd)
        c.add(Calendar.MONTH, monthsToAdd)
        return c.time
    }
}