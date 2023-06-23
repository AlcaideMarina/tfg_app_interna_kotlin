package com.example.hueverianieto.ui.components

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.*
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseComponent
import com.example.hueverianieto.databinding.ComponentMonthYearPickerDialogBinding
import java.util.*

open class HNMonthYearPickerDialog : ConstraintLayout, BaseComponent {

    private var binding: ComponentMonthYearPickerDialogBinding =
        ComponentMonthYearPickerDialogBinding.inflate(
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

    private val MAX_YEAR = 3000
    private val MIN_YEAR = 1950
    private val MONTH_TEXT_ARRAY = arrayOf(
        "Enero",
        "Febrero",
        "Marzo",
        "Abril",
        "Mayo",
        "Junio",
        "Julio",
        "Agosto",
        "Septiembre",
        "Octubre",
        "Noviembre",
        "Diciembre"
    )

    interface OnDateSetListener {
        fun onDateSet(year: Int, month: Int)
    }

    override fun getComponentContext(): Context {
        return this.context
    }

    override fun getView(): View {
        return this
    }

    open fun show(context: Context, rightListener: OnClickListener) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.binding = ComponentMonthYearPickerDialogBinding.inflate(inflater)

        val c = Calendar.getInstance()
        val currentYear = c.get(Calendar.YEAR)
        val currentMonth = c.get(Calendar.MONTH)

        with(this.binding.monthNumberPicker) {
            this.minValue = 1
            this.maxValue = MONTH_TEXT_ARRAY.size - 1
            this.value = currentMonth + 1
            this.displayedValues = MONTH_TEXT_ARRAY
            this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

        with(this.binding.yearNumberPicker) {
            this.minValue = MIN_YEAR
            this.maxValue = MAX_YEAR
            this.value = currentYear
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }

        this.setModalDialogLeftButtonListener { this.cancel() }
        this.setModalDialogRightButtonListener(rightListener)

        this.alertDialog = AlertDialog.Builder(this.context, R.style.DialogTheme).create()
        this.alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.alertDialog.setView(this.binding.root)
        this.alertDialog.setCanceledOnTouchOutside(true)
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

    fun getMonthPicker(): NumberPicker {
        return this.binding.monthNumberPicker
    }

    fun getYearPicker(): NumberPicker {
        return this.binding.yearNumberPicker
    }

    private fun setModalDialogLeftButtonListener(listener: OnClickListener) {
        this.binding.cancelButton.setOnClickListener(listener)
    }

    private fun setModalDialogRightButtonListener(listener: OnClickListener) {
        this.binding.acceptButton.setOnClickListener(listener)
    }

}
