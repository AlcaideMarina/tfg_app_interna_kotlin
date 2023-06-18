package com.example.hueverianieto.ui.views.sellingprice.fragment.modifysellingprice

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
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifySellingPriceBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifySellingPriceFragment : BaseFragment() {

    private lateinit var binding: FragmentModifySellingPriceBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var eggPricesData: EggPricesData
    private val modifySellingPriceViewModel: ModifySellingPriceViewModel by viewModels()
    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        val args: ModifySellingPriceFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.eggPricesData = args.eggPricesData

        this.alertDialog = HNModalDialog(requireContext())

        this.binding = FragmentModifySellingPriceBinding.inflate(
            inflater, container, false
        )

        return this.binding.root
    }

    override fun configureUI() {
        setButton()
        setEditTextInfo(eggPricesData)

        lifecycleScope.launchWhenStarted {
            modifySellingPriceViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.modifySellingPriceViewModel.alertDialog.observe(this) { alertOkData ->
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
                            (activity as BaseActivity).goBackFragments()
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
        this.binding.saveButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso",
                "Esta acción cambiará los precios de los productos a partir de este momento, dejando todos los pedidos anteriores tal y como están.\nEs una acción que puede tener grandes consecuencias.\n¿Está seguro de que quiere continuar?",
                "Atrás",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    val newEggPricesData = setEggPricesData()
                    this.modifySellingPriceViewModel.updateEggSellingPrices(
                        newEggPricesData,
                        currentUserData.documentId!!
                    )
                }
            )

        }
        this.binding.cancelButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifySellingPriceViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButton() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setEditTextInfo(eggPricesData: EggPricesData) {
        with(this.binding) {
            xlBoxEditText.setText(eggPricesData.xlBox.toString())
            xlDozenEditText.setText(eggPricesData.xlDozen.toString())
            lBoxEditText.setText(eggPricesData.lBox.toString())
            lDozenEditText.setText(eggPricesData.lDozen.toString())
            mBoxEditText.setText(eggPricesData.mBox.toString())
            mDozenEditText.setText(eggPricesData.mDozen.toString())
            sBoxEditText.setText(eggPricesData.sBox.toString())
            sDozenEditText.setText(eggPricesData.sDozen.toString())
        }
    }

    private fun setEggPricesData(): EggPricesData {
        return EggPricesData(
            this.binding.xlBoxEditText.text.toString().toDouble(),
            this.binding.xlDozenEditText.text.toString().toDouble(),
            this.binding.lBoxEditText.text.toString().toDouble(),
            this.binding.lDozenEditText.text.toString().toDouble(),
            this.binding.mBoxEditText.text.toString().toDouble(),
            this.binding.mDozenEditText.text.toString().toDouble(),
            this.binding.sBoxEditText.text.toString().toDouble(),
            this.binding.sDozenEditText.text.toString().toDouble(),
        )
    }

    companion object {
        private val TAG = ModifySellingPriceFragment::class.java.simpleName
    }

}
