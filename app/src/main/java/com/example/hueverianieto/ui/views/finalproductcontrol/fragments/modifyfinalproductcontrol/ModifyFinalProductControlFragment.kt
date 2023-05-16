package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.modifyfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.FPCData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFinalProductControlDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.finalproductcontrol.fragments.finalproductcontroldetail.FinalProductControlDetailFragmentArgs
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentFinalProductControlDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var fpcData: FPCData

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        this.binding = FragmentFinalProductControlDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setTexts()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setTexts() {
        with(this.binding) {
            this.layingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.layingDatetime))
            this.packingDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.packingDatetime))
            this.acceptedEggsTextInputLayout.setText(fpcData.acceptedEggs.toString())
            this.rejectedEggsTextInputLayout.setText(fpcData.rejectedEggs.toString())
            this.lotTextInputLayout.setText(fpcData.lot.toString())
            this.bestBeforeDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.bestBeforeDatetime))
            this.issueDateTextInputLayout.setText(
                Utils.parseTimestampToString(
                fpcData.issueDatetime))
        }
    }

}
