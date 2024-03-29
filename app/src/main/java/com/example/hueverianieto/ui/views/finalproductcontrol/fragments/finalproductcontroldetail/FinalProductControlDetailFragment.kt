package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.finalproductcontroldetail

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
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFinalProductControlDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinalProductControlDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentFinalProductControlDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var fpcData: FPCData
    private val finalProductControlDetailViewModel: FinalProductControlDetailViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())
        val args: FinalProductControlDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.fpcData = args.fpcData

        this.binding = FragmentFinalProductControlDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setTexts()
        lifecycleScope.launchWhenStarted {
            finalProductControlDetailViewModel.getFinalProductControl(fpcData.documentId!!)
            finalProductControlDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.finalProductControlDetailViewModel.fpcData.observe(this) { fpcDataObserver ->
            if (fpcDataObserver != null) {
                this.fpcData = fpcDataObserver
                setTexts()
            }
        }
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. Va a eliminar esta información, y puede conllevar consecuencias para la empresa. ¿Está seguro de que quiere continuar?",
                "Atrás",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    this.finalProductControlDetailViewModel.deleteFinalProductControl(
                        fpcData.documentId!!
                    )
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            )
        }
        this.binding.saveButton.setOnClickListener {
            this.finalProductControlDetailViewModel.navigateToModifyFPCResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "fpcData" to fpcData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        state as FinalProductControlDetailViewState
        this.binding.loadingComponent.isVisible = state.isLoading
    }

    private fun setTexts() {
        with(this.binding) {
            this.layingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                    fpcData.layingDatetime
                )
            )
            this.layingDateTextInputLayout.isEnabled = false
            this.packingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                    fpcData.packingDatetime
                )
            )
            this.packingDateTextInputLayout.isEnabled = false
            this.acceptedEggsTextInputLayout.setText(fpcData.acceptedEggs.toString())
            this.acceptedEggsTextInputLayout.isEnabled = false
            this.rejectedEggsTextInputLayout.setText(fpcData.rejectedEggs.toString())
            this.rejectedEggsTextInputLayout.isEnabled = false
            this.lotTextInputLayout.setText(fpcData.lot.toString())
            this.lotTextInputLayout.isEnabled = false
            this.bestBeforeDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                    fpcData.bestBeforeDatetime
                )
            )
            this.bestBeforeDateTextInputLayout.isEnabled = false
            this.issueDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                    fpcData.issueDatetime
                )
            )
            this.issueDateTextInputLayout.isEnabled = false
        }
    }

}
