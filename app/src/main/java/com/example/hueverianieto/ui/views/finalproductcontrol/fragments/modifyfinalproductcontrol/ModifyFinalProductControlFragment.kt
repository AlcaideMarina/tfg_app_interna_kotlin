package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.modifyfinalproductcontrol

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
import com.example.hueverianieto.ui.views.finalproductcontrol.fragments.finalproductcontroldetail.FinalProductControlDetailFragmentArgs
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class ModifyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentFinalProductControlDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var fpcData: FPCData
    private val modifyFinalProductControlViewModel: ModifyFinalProductControlViewModel by viewModels()

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

        val args: ModifyFinalProductControlFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.fpcData = args.fpcData

        this.binding = FragmentFinalProductControlDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setTexts()
        lifecycleScope.launchWhenStarted {
            modifyFinalProductControlViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun updateUI(state: BaseState) {
        state as ModifyFinalProductControlViewState
        this.binding.loadingComponent.isVisible = state.isLoading
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setTexts() {
        with(this.binding) {
            this.layingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.layingDatetime))
            this.layingDateTextInputLayout.setOnClickListener { onClickScheduledLayingDate() }
            this.packingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.packingDatetime))
            this.packingDateTextInputLayout.setOnClickListener { onClickScheduledPackingDate() }
            this.acceptedEggsTextInputLayout.setText(fpcData.acceptedEggs.toString())
            this.rejectedEggsTextInputLayout.setText(fpcData.rejectedEggs.toString())
            this.lotTextInputLayout.setText(fpcData.lot.toString())
            this.bestBeforeDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.bestBeforeDatetime))
            this.bestBeforeDateTextInputLayout.setOnClickListener { onClickScheduledBestBeforeDate() }
            this.issueDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.issueDatetime))
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
