package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.newboxesandcartonsresources

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewBoxesAndCartonsResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewBoxesAndCartonsResourcesBinding
    private lateinit var currentUserData: InternalUserData

    private lateinit var alertDialog: HNModalDialog
    private lateinit var datetimeSelected: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: NewBoxesAndCartonsResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewBoxesAndCartonsResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setFields()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setButtons() {
        with(this.binding) {
            this.cancelButton.setText("Cancelar")
            this.saveButton.setText("Guardar")
        }
    }

    private fun setFields() {
        with(this.binding) {
            datetimeSelected = Timestamp(Date())
            this.dateTextInputLayout.setText(
                Utils.parseDateToString(datetimeSelected.toDate())
            )
            this.dateTextInputLayout.setOnClickListener { onClickScheduledDate() }
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
