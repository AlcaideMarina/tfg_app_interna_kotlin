package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.electricitywatergasresourcesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class ElectricityWaterGasResourcesDetail : BaseFragment() {

    private lateinit var binding: FragmentElectricityWaterGasResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var ewgResourcesData: ElectricityWaterGasResourcesData

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
