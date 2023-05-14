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
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.hensresouces.fragments.modifyhensresources.ModifyHensResourcesFragmentArgs
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ModifyElectricityWaterGasResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentElectricityWaterGasResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var ewgResourcesData: ElectricityWaterGasResourcesData
    private val modifyElectricityWaterGasResourcesViewModel: ModifyElectricityWaterGasResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    private var dropdownTypesItems : MutableList<String> = mutableListOf()

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
        setDropdownRoleOptions()

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
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifyElectricityWaterGasResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
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

    private fun setDropdownRoleOptions() {
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
