package com.example.hueverianieto.ui.views.internalusers.fragments.modifyinternaluser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifyInternalUserBinding
import com.example.hueverianieto.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyInternalUserFragment : BaseFragment() {

    private lateinit var binding : FragmentModifyInternalUserBinding
    private lateinit var currentUserData : InternalUserData
    private lateinit var internalUserData : InternalUserData
    private var dropdownRoleItems : MutableList<String> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)
        this.binding = FragmentModifyInternalUserBinding.inflate(
            inflater, container, false
        )

        val args : ModifyInternalUserFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        setFieldText()
        setDropdownRoleOptions()
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

    private fun setFieldText() {
        with(this.binding) {
            nameTextInputLayout.setText(internalUserData.name)
            surnameTextInputLayout.setText(internalUserData.surname)
            dniTextInputLayout.setText(internalUserData.dni)
            phoneTextInputLayout.setText(internalUserData.phone.toString())
            emailTextInputLayout.setText(internalUserData.email)
            directionTextInputLayout.setText(internalUserData.direction)
            cityTextInputLayout.setText(internalUserData.city)
            provinceTextInputLayout.setText(internalUserData.province)
            postalCodeTextInputLayout.setText(internalUserData.postalCode.toString())
            ssNumberTextInputLayout.setText(internalUserData.ssNumber.toString())
            bankAccountTextInputLayout.setText(internalUserData.bankAccount)
            roleAutoCompleteTextView.setText(internalUserData.position.toString())
            userAccountTextInputLayout.setText(internalUserData.user)
            userAccountTextInputLayout.isEnabled = false
        }
    }

    private fun setDropdownRoleOptions() {
        val values = Constants.roles.entries.iterator()
        for (v in values) {
            dropdownRoleItems.add(requireContext().getString(v.key))
        }

        this.binding.roleAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                this.requireContext(), R.layout.component_dropdown_list_item,
                dropdownRoleItems
            )
        )

    }

}
