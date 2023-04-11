package com.example.hueverianieto.data

import android.view.View

data class ModalDialogModel (
    var title: String,
    var text: String,
    var leftButtonText: String,
    var rightButtonText: String?,
    var leftButtonListener: View.OnClickListener,
    var rightButtonListener: View.OnClickListener?,
    var cancelledOnTouchOutside: Boolean
)