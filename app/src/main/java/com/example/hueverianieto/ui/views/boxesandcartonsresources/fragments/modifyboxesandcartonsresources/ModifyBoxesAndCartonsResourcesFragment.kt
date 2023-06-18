package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.modifyboxesandcartonsresources

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
import com.example.hueverianieto.databinding.FragmentBoxesAndCartonsResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.MaterialUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentBoxesAndCartonsResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var bcResourcesData: BoxesAndCartonsResourcesData
    private val modifyBoxesAndCartonsResourcesViewModel: ModifyBoxesAndCartonsResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: ModifyBoxesAndCartonsResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.bcResourcesData = args.bcResourcesData

        this.binding = FragmentBoxesAndCartonsResourcesDetailBinding
            .inflate(inflater, container, false)

        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setText()
        lifecycleScope.launchWhenStarted {
            modifyBoxesAndCartonsResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.modifyBoxesAndCartonsResourcesViewModel.alertDialog.observe(this) { alertOkData ->
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
                    this.bcResourcesData.order = MaterialUtils
                        .parseDBBoxesAndCartonsOrderFieldDataToMap(getOrderFieldStructure())
                    this.bcResourcesData.totalPrice =
                        this.binding.totalPriceTextInputLayout.text.toString().toDouble()
                    this.modifyBoxesAndCartonsResourcesViewModel.updateBoxesAndCartons(
                        bcResourcesData
                    )
                }
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifyBoxesAndCartonsResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text =
                com.example.hueverianieto.utils.Utils.parseTimestampToString(bcResourcesData.expenseDatetime)
            this.boxesTextInputLayout.setText((bcResourcesData.order["box"] ?: "").toString())
            this.xlCartonsTextInputLayout.setText(
                (bcResourcesData.order["xl_carton"] ?: "").toString()
            )
            this.lCartonsTextInputLayout.setText(
                (bcResourcesData.order["l_carton"] ?: "").toString()
            )
            this.mCartonsTextInputLayout.setText(
                (bcResourcesData.order["m_carton"] ?: "").toString()
            )
            this.sCartonsTextInputLayout.setText(
                (bcResourcesData.order["s_carton"] ?: "").toString()
            )
            this.totalPriceTextInputLayout.setText(bcResourcesData.totalPrice.toString())
        }
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
