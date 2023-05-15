package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.newelectricitywatergasresources

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ElectricityWaterGasResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewElectricityWaterGasResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewElectricityWaterGasResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewElectricityWaterGasResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val newElectricityWaterGasResourcesViewModel: NewElectricityWaterGasResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    private lateinit var datetimeSelected: Timestamp
    private var dropdownTypesItems : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: NewElectricityWaterGasResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewElectricityWaterGasResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setFields()
        setDropdownTypesOptions()

        lifecycleScope.launchWhenStarted {
            newElectricityWaterGasResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newElectricityWaterGasResourcesViewModel.alertDialog.observe(this) { alertOkData ->
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
                    this.binding.typeAutoCompleteTextView.text != null && this.binding.typeAutoCompleteTextView.text.toString() != "" &&
                    this.binding.totalPriceTextInputLayout.text != null && this.binding.totalPriceTextInputLayout.text.toString() != "") {
                it.hideSoftInput()
                val typeSelected : Int? = when(this.binding.typeAutoCompleteTextView.text.toString()) {
                    resources.getString(R.string.electricity) -> R.string.electricity
                    resources.getString(R.string.water) -> R.string.water
                    resources.getString(R.string.gas) -> R.string.gas
                    else -> null
                }
                if(typeSelected != null && this.binding.totalPriceTextInputLayout.text != null &&
                    this.binding.totalPriceTextInputLayout.text.toString() != "") {
                    val ewgResourcesData = ElectricityWaterGasResourcesData(
                        this.currentUserData.documentId!!,
                        Timestamp.now(),
                        false,
                        null,
                        datetimeSelected,
                        (this.binding.notesTextInputLayout.text ?: "").toString(),
                        this.binding.totalPriceTextInputLayout.text.toString().toDouble(),
                        Constants.ewgTypes[typeSelected]!!.toLong()
                    )

                    this.newElectricityWaterGasResourcesViewModel.addEWGResource(
                        ewgResourcesData
                    )
                    activity?.onBackPressedDispatcher?.onBackPressed()
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        "Formulario incorrecto",
                        "Es obligatorio introducir el tipo de gasto y el precio total. Por favor, revise los datos y vuelva a intentarlo.",
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
        with(state as NewElectricityWaterGasResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
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

    private fun setDropdownTypesOptions() {
        val values = Constants.ewgTypes.entries.iterator()
        for (v in values) {
            dropdownTypesItems.add(requireContext().getString(v.key))
        }

        this.binding.typeAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                this.requireContext(), R.layout.component_dropdown_list_item,
                dropdownTypesItems
            )
        )

    }

}