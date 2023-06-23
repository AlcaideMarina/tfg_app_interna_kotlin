package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.modifyelectricitywatergasresources

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
import com.example.hueverianieto.databinding.FragmentElectricityWaterGasResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyElectricityWaterGasResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentElectricityWaterGasResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var ewgResourcesData: ElectricityWaterGasResourcesData
    private val modifyElectricityWaterGasResourcesViewModel: ModifyElectricityWaterGasResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    private var dropdownTypesItems: MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())
        val args: ModifyElectricityWaterGasResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.ewgResourcesData = args.ewgResourcesData

        this.binding = FragmentElectricityWaterGasResourcesDetailBinding
            .inflate(inflater, container, false)

        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setText()
        setDropdownTypesOptions()

        lifecycleScope.launchWhenStarted {
            modifyElectricityWaterGasResourcesViewModel.viewState.collect { viewState ->
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
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso",
                "Va a modificar este ticket. ¿Quiere continuar con el proceso?",
                "Cancelar",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    val typeSelected: Int? =
                        when (this.binding.typeAutoCompleteTextView.text.toString()) {
                            resources.getString(R.string.electricity) -> R.string.electricity
                            resources.getString(R.string.water) -> R.string.water
                            resources.getString(R.string.gas) -> R.string.gas
                            else -> null
                        }
                    if (typeSelected != null && this.binding.totalPriceTextInputLayout.text != null &&
                        this.binding.totalPriceTextInputLayout.text.toString() != ""
                    ) {
                        this.ewgResourcesData.type = Constants.ewgTypes[typeSelected]!!.toLong()
                        this.ewgResourcesData.totalPrice =
                            this.binding.totalPriceTextInputLayout.text.toString().toDouble()
                        this.ewgResourcesData.notes =
                            (this.binding.notesTextInputLayout.text ?: "").toString()

                        this.modifyElectricityWaterGasResourcesViewModel.updateEWG(
                            this.ewgResourcesData
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
                }
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifyElectricityWaterGasResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButtonText.text = "Guardar"
        this.binding.cancelButtonText.text = "Cancelar"
    }

    private fun setText() {
        val key = Utils.getKey(Constants.ewgTypes, ewgResourcesData.type.toInt())
        val type: String = if (key == null) {
            ""
        } else {
            resources.getString(key)
        }

        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(ewgResourcesData.expenseDatetime)
            this.typeAutoCompleteTextView.setText(type)
            this.typeAutoCompleteTextView.isEnabled = false
            this.totalPriceTextInputLayout.setText(ewgResourcesData.totalPrice.toString())
            this.notesTextInputLayout.setText(ewgResourcesData.notes)
        }
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
