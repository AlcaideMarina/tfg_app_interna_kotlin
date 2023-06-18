package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.boxesandcartonsresourcesdetail

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
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentBoxesAndCartonsResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoxesAndCartonsResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentBoxesAndCartonsResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var bcResourcesData: BoxesAndCartonsResourcesData
    private val boxesAndCartonsResourcesDetailViewModel: BoxesAndCartonsResourcesDetailViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: BoxesAndCartonsResourcesDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.bcResourcesData = args.bcResourcesData

        this.binding = FragmentBoxesAndCartonsResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setText()
        lifecycleScope.launchWhenStarted {
            boxesAndCartonsResourcesDetailViewModel.getBoxesAndCartonsResource(
                bcResourcesData.documentId!!
            )
            boxesAndCartonsResourcesDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.boxesAndCartonsResourcesDetailViewModel.bcResource.observe(this) { bcResourcesDataObserver ->
            if (bcResourcesDataObserver != null) {
                bcResourcesData = bcResourcesDataObserver
                setText()
            }
        }
        this.boxesAndCartonsResourcesDetailViewModel.alertDialog.observe(this) { alertOkData ->
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
                    this.boxesAndCartonsResourcesDetailViewModel
                        .deleteBoxesAndCartonsResources(bcResourcesData.documentId!!)
                }
            )
        }
        this.binding.saveButton.setOnClickListener {
            this.boxesAndCartonsResourcesDetailViewModel.navigationToModifyBCResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "bcResourcesData" to bcResourcesData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as BoxesAndCartonsResourcesDetailViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.setText("Eliminar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(bcResourcesData.expenseDatetime)
            this.boxesTextInputLayout.setText((bcResourcesData.order["box"] ?: "").toString())
            this.boxesTextInputLayout.isEnabled = false
            this.xlCartonsTextInputLayout.setText(
                (bcResourcesData.order["xl_carton"] ?: "").toString()
            )
            this.xlCartonsTextInputLayout.isEnabled = false
            this.lCartonsTextInputLayout.setText(
                (bcResourcesData.order["l_carton"] ?: "").toString()
            )
            this.lCartonsTextInputLayout.isEnabled = false
            this.mCartonsTextInputLayout.setText(
                (bcResourcesData.order["m_carton"] ?: "").toString()
            )
            this.mCartonsTextInputLayout.isEnabled = false
            this.sCartonsTextInputLayout.setText(
                (bcResourcesData.order["s_carton"] ?: "").toString()
            )
            this.sCartonsTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setText(bcResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
        }
    }

}
