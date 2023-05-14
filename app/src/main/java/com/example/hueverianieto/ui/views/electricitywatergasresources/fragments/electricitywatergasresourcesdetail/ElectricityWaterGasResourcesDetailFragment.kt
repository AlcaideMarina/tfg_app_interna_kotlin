package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.electricitywatergasresourcesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
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
class ElectricityWaterGasResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentElectricityWaterGasResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var ewgResourcesData: ElectricityWaterGasResourcesData
    private val ewgResourcesDetailViewModel: ElectricityWaterGasResourcesDetailViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: ElectricityWaterGasResourcesDetailArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.ewgResourcesData = args.ewgResourcesData

        this.binding = FragmentElectricityWaterGasResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setText()
        lifecycleScope.launchWhenStarted {
            ewgResourcesDetailViewModel.getEWGResource(ewgResourcesData.documentId!!)
            ewgResourcesDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.ewgResourcesDetailViewModel.ewgResource.observe(this) { ewgResourcesDataObserver ->
            if (ewgResourcesDataObserver != null) {
                ewgResourcesData = ewgResourcesDataObserver
                setText()
            }
        }
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. Va a eliminar este ticket, y puede conllevar consecuencias para la empresa. ¿Está seguro de que quiere continuar?",
                "Atrás",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    this.ewgResourcesDetailViewModel
                        .deleteEWGResources(ewgResourcesData.documentId!!)
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            )
        }
        this.binding.saveButton.setOnClickListener {
            this.ewgResourcesDetailViewModel.navigateToModifyEWGResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "ewgResourcesData" to ewgResourcesData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ElectricityWaterGasResourcesDetailViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.setText("Eliminar")
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
            this.typeTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setText(ewgResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
            this.notesTextInputLayout.setText(ewgResourcesData.notes)
            this.notesTextInputLayout.isEnabled = false
        }
    }

}
