package com.example.hueverianieto.ui.views.internalusers.fragments.internaluserdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentInternalUserDetailBinding

class InternalUserDetailFragment : BaseFragment() {

    private lateinit var binding : FragmentInternalUserDetailBinding
    private lateinit var currentUserData : InternalUserData
    private lateinit var internalUserData : InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentInternalUserDetailBinding.inflate(
            inflater, container, false
        )

        val args : InternalUserDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
        setFieldText()
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
        this.binding.cancelButton.setText("Eliminar usuario")
    }

    private fun setFieldText() {
        with(this.binding) {
            nameTextInputLayout.setText(internalUserData.name)
            nameTextInputLayout.isEnabled = false
            surnameTextInputLayout.setText(internalUserData.surname)
            surnameTextInputLayout.isEnabled = false
            dniTextInputLayout.setText(internalUserData.dni)
            dniTextInputLayout.isEnabled = false
            phoneTextInputLayout.setText(internalUserData.phone.toString())
            phoneTextInputLayout.isEnabled = false
            emailTextInputLayout.setText(internalUserData.email)
            emailTextInputLayout.isEnabled = false
            directionTextInputLayout.setText(internalUserData.direction)
            directionTextInputLayout.isEnabled = false
            cityTextInputLayout.setText(internalUserData.city)
            cityTextInputLayout.isEnabled = false
            provinceTextInputLayout.setText(internalUserData.province)
            provinceTextInputLayout.isEnabled = false
            postalCodeTextInputLayout.setText(internalUserData.postalCode.toString())
            postalCodeTextInputLayout.isEnabled = false
            ssNumberTextInputLayout.setText(internalUserData.ssNumber.toString())
            ssNumberTextInputLayout.isEnabled = false
            bankAccountTextInputLayout.setText(internalUserData.bankAccount)
            bankAccountTextInputLayout.isEnabled = false
            roleAutoCompleteTextView.setText(internalUserData.position.toString())
            roleAutoCompleteTextView.isEnabled = false
            roleTextInputLayout.isEnabled = false
            userAccountTextInputLayout.setText(internalUserData.user)
            userAccountTextInputLayout.isEnabled = false
        }
    }

}
