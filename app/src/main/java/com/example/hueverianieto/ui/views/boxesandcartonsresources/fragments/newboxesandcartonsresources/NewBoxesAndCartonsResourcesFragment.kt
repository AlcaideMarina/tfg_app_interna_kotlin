package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.newboxesandcartonsresources

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
import com.example.hueverianieto.data.models.local.DBBoxesAndCartonsOrderFieldData
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewBoxesAndCartonsResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.MaterialUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NewBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewBoxesAndCartonsResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val newBoxesAndCartonsResourcesViewModel: NewBoxesAndCartonsResourcesViewModel by viewModels()

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
        lifecycleScope.launchWhenStarted {
            newBoxesAndCartonsResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            it.hideSoftInput()
            val orderFieldStructure = getOrderFieldStructure()
            val emptyOrder = DBBoxesAndCartonsOrderFieldData(
                (0).toLong(), (0).toLong(), (0).toLong(), (0).toLong(), (0).toLong()
            )
            if (this.binding.dateTextInputLayout.text != null && this.binding.dateTextInputLayout.text.toString() != "" &&
                orderFieldStructure != emptyOrder &&
                this.binding.totalPriceTextInputLayout.text != null && this.binding.totalPriceTextInputLayout.text.toString() != ""
            ) {
                it.hideSoftInput()
                val bcResourcesData = BoxesAndCartonsResourcesData(
                    this.currentUserData.documentId!!,
                    Timestamp.now(),
                    false,
                    null,
                    datetimeSelected,
                    MaterialUtils
                        .parseDBBoxesAndCartonsOrderFieldDataToMap(getOrderFieldStructure()),
                    this.binding.totalPriceTextInputLayout.text.toString().toDouble()
                )

                this.newBoxesAndCartonsResourcesViewModel.addBoxesAndCartonsResource(
                    bcResourcesData
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
        state as NewBoxesAndCartonsResourcesViewState
        binding.loadingComponent.isVisible = state.isLoading
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

    private fun getOrderFieldStructure(): DBBoxesAndCartonsOrderFieldData {
        val box =
            if (this.binding.boxesTextInputLayout.text == null || this.binding.boxesTextInputLayout.text.toString() == "") "0"
            else this.binding.boxesTextInputLayout.text.toString()
        val xlCartons =
            if (this.binding.xlCartonsTextInputLayout.text == null || this.binding.xlCartonsTextInputLayout.text.toString() == "") "0"
            else this.binding.xlCartonsTextInputLayout.text.toString()
        val lCartons =
            if (this.binding.lCartonsTextInputLayout.text == null || this.binding.lCartonsTextInputLayout.text.toString() == "") "0"
            else this.binding.lCartonsTextInputLayout.text.toString()
        val mCartons =
            if (this.binding.mCartonsTextInputLayout.text == null || this.binding.mCartonsTextInputLayout.text.toString() == "") "0"
            else this.binding.mCartonsTextInputLayout.text.toString()
        val sCartons =
            if (this.binding.sCartonsTextInputLayout.text == null || this.binding.sCartonsTextInputLayout.text.toString() == "") "0"
            else this.binding.sCartonsTextInputLayout.text.toString()
        return DBBoxesAndCartonsOrderFieldData(
            box = box.toLong(),
            xlCarton = xlCartons.toLong(),
            lCarton = lCartons.toLong(),
            mCarton = mCartons.toLong(),
            sCarton = sCartons.toLong(),
        )
    }

}
