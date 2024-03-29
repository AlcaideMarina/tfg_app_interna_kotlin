package com.example.hueverianieto.ui.views.feedresources.fragments.newfeedresources

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
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewFeedResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewFeedResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewFeedResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val newFeedResourcesViewModel: NewFeedResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog
    private lateinit var datetimeSelected: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())
        val args: NewFeedResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewFeedResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setFields()
        lifecycleScope.launchWhenStarted {
            newFeedResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newFeedResourcesViewModel.alertDialog.observe(this) { alertOkData ->
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
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            it.hideSoftInput()
            if (this.binding.dateTextInputLayout.text != null && this.binding.dateTextInputLayout.text.toString() != "" &&
                this.binding.kilosTextInputLayout.text != null && this.binding.kilosTextInputLayout.text.toString() != "" &&
                this.binding.totalPriceTextInputLayout.text != null && this.binding.totalPriceTextInputLayout.text.toString() != ""
            ) {
                it.hideSoftInput()
                val feedResourcesData = FeedResourcesData(
                    this.currentUserData.documentId!!,
                    Timestamp.now(),
                    false,
                    null,
                    datetimeSelected,
                    this.binding.kilosTextInputLayout.text.toString().toDouble(),
                    this.binding.totalPriceTextInputLayout.text.toString().toDouble()
                )

                this.newFeedResourcesViewModel.addFeedResource(
                    feedResourcesData
                )
                activity?.onBackPressedDispatcher?.onBackPressed()
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
        with(state as NewFeedResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
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
