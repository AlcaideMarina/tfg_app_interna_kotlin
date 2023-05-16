package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.newfinalproductcontrol

import android.app.DatePickerDialog
import android.os.Bundle
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
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFinalProductControlDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*
import kotlin.properties.Delegates

@AndroidEntryPoint
class NewFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentFinalProductControlDetailBinding
    private lateinit var currentUserData: InternalUserData
    private var lastLot by Delegates.notNull<Long>()
    private val newFinalProductControlViewModel: NewFinalProductControlViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog
    private lateinit var layingDatetimeSelected: Timestamp
    private lateinit var packingDatetimeSelected: Timestamp
    private lateinit var bestBeforeDatetimeSelected: Timestamp
    private lateinit var issueDatetimeSelected: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: NewFinalProductControlFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.lastLot = args.lastLot

        this.binding = FragmentFinalProductControlDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setFields()
        lifecycleScope.launchWhenStarted {
            newFinalProductControlViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            it.hideSoftInput()
            if (this.binding.layingDateTextInputLayout.text != null && this.binding.layingDateTextInputLayout.text.toString() != "" &&
                    this.binding.packingDateTextInputLayout.text != null && this.binding.packingDateTextInputLayout.text.toString() != "" &&
                    this.binding.acceptedEggsTextInputLayout.text != null && this.binding.acceptedEggsTextInputLayout.text.toString() != "" &&
                    this.binding.rejectedEggsTextInputLayout.text != null && this.binding.rejectedEggsTextInputLayout.text.toString() != "" &&
                    this.binding.lotTextInputLayout.text != null && this.binding.lotTextInputLayout.text.toString() != "" &&
                    this.binding.bestBeforeDateTextInputLayout.text != null && this.binding.bestBeforeDateTextInputLayout.text.toString() != "" &&
                    this.binding.issueDateTextInputLayout.text != null && this.binding.issueDateTextInputLayout.text.toString() != "") {
                if (layingDatetimeSelected <= packingDatetimeSelected && layingDatetimeSelected <= packingDatetimeSelected
                        && layingDatetimeSelected <= bestBeforeDatetimeSelected && layingDatetimeSelected <= issueDatetimeSelected) {
                    this.newFinalProductControlViewModel.addFPC(
                        FPCData(
                            this.binding.acceptedEggsTextInputLayout.text.toString().toLong(),
                            bestBeforeDatetimeSelected,
                            currentUserData.documentId!!,
                            Timestamp.now(),
                            false,
                            null,
                            issueDatetimeSelected,
                            layingDatetimeSelected,
                            this.binding.lotTextInputLayout.text.toString().toLong(),
                            packingDatetimeSelected,
                            this.binding.rejectedEggsTextInputLayout.text.toString().toLong()
                        )
                    )
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        "Fechas incorrectas",
                        "Las fechas introducidas no son correctas. Por favor, revise los datos e inténtelo de nuevo.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
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
        with(state as NewFinalProductControlViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setFields() {
        with(this.binding) {
            lotTextInputLayout.setText((lastLot + 1).toString())
            // Laying date
            layingDatetimeSelected = Timestamp(Date())
            this.layingDateTextInputLayout.setText(
                Utils.parseDateToString(layingDatetimeSelected.toDate())
            )
            this.layingDateTextInputLayout.setOnClickListener { onClickScheduledLayingDate() }
            // packing date
            packingDatetimeSelected = Timestamp(Date())
            this.packingDateTextInputLayout.setText(
                Utils.parseDateToString(packingDatetimeSelected.toDate())
            )
            this.packingDateTextInputLayout.setOnClickListener { onClickScheduledPackingDate() }
            // best before date
            bestBeforeDatetimeSelected = Timestamp(Date())
            this.bestBeforeDateTextInputLayout.setText(
                Utils.parseDateToString(bestBeforeDatetimeSelected.toDate())
            )
            this.bestBeforeDateTextInputLayout.setOnClickListener { onClickScheduledBestBeforeDate() }
            // issue date
            issueDatetimeSelected = Timestamp(Date())
            this.issueDateTextInputLayout.setText(
                Utils.parseDateToString(issueDatetimeSelected.toDate())
            )
            this.issueDateTextInputLayout.setOnClickListener { onClickScheduledIssueDate() }
        }
    }

    private fun onClickScheduledLayingDate() {
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
            this.binding.layingDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            layingDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }

    private fun onClickScheduledPackingDate() {
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
            this.binding.packingDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            packingDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.show()
    }

    private fun onClickScheduledBestBeforeDate() {
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
            this.binding.bestBeforeDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            bestBeforeDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.show()
    }

    private fun onClickScheduledIssueDate() {
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
            this.binding.issueDateTextInputLayout.setText("$dayStr/$monthStr/$yearStr")
            issueDatetimeSelected = Utils.parseStringToTimestamp("$dayStr/$monthStr/$yearStr")
        }
        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.show()
    }

}
