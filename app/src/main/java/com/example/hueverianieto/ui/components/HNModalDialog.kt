package com.example.hueverianieto.ui.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentModalDialogBinding
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel

open class HNModalDialog : ConstraintLayout, BaseComponent {

    private var binding: ComponentModalDialogBinding = ComponentModalDialogBinding.inflate(
        LayoutInflater.from(this.context)
    )
    private lateinit var alertDialog: AlertDialog

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        this.addView(this.binding.root, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

    open fun show(context: Context, model: ModalDialogModel) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.binding = ComponentModalDialogBinding.inflate(inflater)
        this.setModalDialogTitle(model.title)
        this.setModalDialogText(model.text)
        this.setModalDialogLeftButtonText(model.leftButtonText)
        this.setModalDialogLeftButtonListener(model.leftButtonListener)
        if (model.rightButtonText != null) {
            this.setModalDialogButtons(true)
            this.setModalDialogRightButtonText(model.rightButtonText!!)
            this.setModalDialogRightButtonListener(model.rightButtonListener!!)
        } else {
            this.setModalDialogButtons(false)
        }
        this.alertDialog = AlertDialog.Builder(this.context, R.style.DialogTheme).create()
        this.alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.alertDialog.setView(this.binding.root)
        this.alertDialog.setCanceledOnTouchOutside(model.cancelledOnTouchOutside)
        val window: Window? = alertDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        this.alertDialog.show()
    }

    open fun cancel() {
        this.alertDialog.cancel()
    }

    private fun setModalDialogTitle(title: String) {
        this.binding.modalDialogTitle.text = title
    }

    private fun setModalDialogText(text: String) {
        this.binding.modalDialogText.text = text
    }

    private fun setModalDialogLeftButtonText(text: String) {
        this.binding.modalDialogLeftButton.text = text
    }

    private fun setModalDialogRightButtonText(text: String) {
        this.binding.modalDialogRightButton.text = text
    }

    private fun setModalDialogButtons(isRightButton: Boolean) {
        this.binding.modalDialogRightButton.isVisible = isRightButton
        this.binding.verticalDivider.isVisible = isRightButton
    }

    private fun setModalDialogLeftButtonListener(listener: OnClickListener) {
        this.binding.modalDialogLeftButton.setOnClickListener(listener)
    }

    private fun setModalDialogRightButtonListener(listener: OnClickListener) {
        this.binding.modalDialogRightButton.setOnClickListener(listener)
    }

}
