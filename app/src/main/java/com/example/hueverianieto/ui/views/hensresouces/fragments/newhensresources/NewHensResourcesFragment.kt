package com.example.hueverianieto.ui.views.hensresouces.fragments.newhensresources

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewHensResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class NewHensResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewHensResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val newHensResourcesViewModel: NewHensResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    private lateinit var datetimeSelected : Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args : NewHensResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewHensResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setFields()
        lifecycleScope.launchWhenStarted {
            newHensResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newHensResourcesViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 0) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        },
                        null
                    )
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            if (this.binding.dateTextInputLayout.text != null && this.binding.dateTextInputLayout.text.toString() != "" &&
                this.binding.quantityTextInputLayout.text != null && this.binding.quantityTextInputLayout.text.toString() != "" &&
                this.binding.shedATextInputLayout.text != null && this.binding.shedATextInputLayout.text.toString() != "" &&
                this.binding.shedBTextInputLayout.text != null && this.binding.shedBTextInputLayout.text.toString() != "" &&
                this.binding.totalPriceTextInputLayout.text != null && this.binding.totalPriceTextInputLayout.text.toString() != "") {

                val quantity = this.binding.quantityTextInputLayout.text.toString().toInt()
                val shedAStr = (this.binding.shedATextInputLayout.text ?: 0).toString()
                val shedBStr = (this.binding.shedBTextInputLayout.text ?: 0).toString()

                val shedA = if (shedAStr == "") 0 else shedAStr.toInt()
                val shedB = if (shedBStr == "") 0 else shedBStr.toInt()

                if (quantity != (shedA + shedB)) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        "Distribución de gallinas",
                        "La distribución de las gallinas es incorrecta. Recuerde que las gallinas totales deben resultar de la suma de las que se introducen en cada nave.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                } else {
                    this.newHensResourcesViewModel.addHensResource(
                        HensResourcesData(
                            this.currentUserData.documentId!!,
                            Timestamp.now(),
                            false,
                            null,
                            datetimeSelected,
                            quantity.toLong(),
                            shedA.toLong(),
                            shedB.toLong(),
                            this.binding.totalPriceTextInputLayout.text.toString().toDouble()
                        )
                    )
                }

            } else {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Formulario incompleto",
                    "Debe rellenar todos los campos del formulario. Por favor, revise lod datos e inténtelo de nuevo.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as NewHensResourcesViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        with(this.binding) {
            this.cancelButton.setText("Cancelar")
            this.saveButton.setText("Guardar")
        }
    }

    private fun setFields() {
        with(this.binding) {
            this.shedATextInputLayout.isEnabled = false
            this.shedBTextInputLayout.isEnabled = false
            this.quantityTextInputLayout.addTextChangedListener(watcherHensNumber)

            datetimeSelected = Timestamp(Date())
            this.dateTextInputLayout.setText(
                Utils.parseDateToString(datetimeSelected.toDate())
            )
            this.dateTextInputLayout.setOnClickListener { onClickScheduledDate() }
        }
    }

    private val watcherHensNumber: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (this@NewHensResourcesFragment.binding.quantityTextInputLayout.text.toString().isEmpty()) {
                this@NewHensResourcesFragment.binding.shedATextInputLayout.setText("")
                this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = false
                this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText("")
                this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = false
            } else {
                try {
                    val hensNumber = s.toString().toInt()
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = true
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = true
                    if (hensNumber % 2 == 0) {
                        this@NewHensResourcesFragment.binding.shedATextInputLayout.setText(
                            (hensNumber / 2).toString())
                    } else {
                        this@NewHensResourcesFragment.binding.shedATextInputLayout.setText(
                            ((hensNumber / 2) + 1).toString())
                    }
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText(
                        ((hensNumber / 2)).toString())
                } catch (e: Exception) {
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.setText("")
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText("")
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = false
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = false
                }
            }
        }

    }

    private fun onClickScheduledDate() {
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val day = selectedCalendar.get(Calendar.DATE)
        val listener = DatePickerDialog.OnDateSetListener { _, y, m, d ->
            var dayStr = d.toString()
            var monthStr = (m + 1).toString()
            var yearStr = y.toString()
            if (dayStr.length < 2) dayStr = "0$dayStr"
            if (monthStr.length < 2) monthStr = "0$monthStr"
            if (yearStr.length < 4) yearStr = "0$yearStr"
            this.binding.dateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            datetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }

}
