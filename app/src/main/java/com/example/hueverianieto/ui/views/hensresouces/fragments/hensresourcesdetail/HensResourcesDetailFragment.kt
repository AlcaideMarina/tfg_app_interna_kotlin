package com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail

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
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ComponentTicketBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HensResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentHensResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var hensResourcesData: HensResourcesData

    private val hensResourcesDetailViewModel: HensResourcesDetailViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args : HensResourcesDetailFragmentArgs by navArgs()
        this.hensResourcesData = args.hensResourcesData
        this.currentUserData = args.currentUserData

        this.binding = FragmentHensResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        setText()
        lifecycleScope.launchWhenStarted {
            hensResourcesDetailViewModel.getHensResource(hensResourcesData.documentId!!)
            hensResourcesDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.hensResourcesDetailViewModel.henResource.observe(this) { hensResourcesDataObserver ->
            if (hensResourcesDataObserver != null) {
                hensResourcesData = hensResourcesDataObserver
                setText()
            }
        }
        this.hensResourcesDetailViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    alertOkData.title,
                    alertOkData.text,
                    "De acuerdo",
                    null,
                    {
                        alertDialog.cancel()
                        (activity as BaseActivity).goBackFragments()
                    },
                    null
                )
            }
        }
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            this.hensResourcesDetailViewModel.navigateToModifyHensResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "hensResourcesData" to hensResourcesData
                )
            )
        }
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
                    this.hensResourcesDetailViewModel
                        .deleteHenResources(hensResourcesData.documentId!!)
                }
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as HensResourcesDetailViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.setText("Eliminar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(hensResourcesData.expenseDatetime)
            this.quantityTextInputLayout.setText(hensResourcesData.hensNumber.toString())
            this.quantityTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setText(hensResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
        }
    }

}
