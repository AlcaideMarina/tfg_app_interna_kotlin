package com.example.hueverianieto.ui.views.clients.fragments.modifyclient

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifyClientBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyClientFragment : BaseFragment() {

    private lateinit var binding : FragmentModifyClientBinding
    private lateinit var clientData : ClientData
    private lateinit var currentUserData : InternalUserData
    private val modifyClientViewModel : ModifyClientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)
        this.binding = FragmentModifyClientBinding.inflate(
            inflater, container, false
        )

        val args : ModifyClientFragmentArgs by navArgs()
        this.clientData = args.clientData
        this.currentUserData = args.currentUserData

        return this.binding.root

    }

    override fun configureUI() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
        if (clientData.hasAccount) {
            this.binding.containerBorderCheckTitle.visibility = View.GONE
        }
        setFieldTexts()
        setFieldTypes()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.checkedTextView.setOnClickListener {
            this.binding.checkedTextView.isChecked = !this.binding.checkedTextView.isChecked
            this.binding.userAccountTextInputLayout.isEnabled = this.binding.checkedTextView.isChecked
            this.binding.emailAccountTextInputLayout.isEnabled = this.binding.checkedTextView.isChecked
        }
        this.binding.cancelButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            // TODO: Actualizar clientdata
            var user : String? = null
            var hasAccount : Boolean = false

            if (clientData.hasAccount) {
                hasAccount = true
                user = clientData.user!!
            } else {
                if (this.binding.checkedTextView.isChecked) {
                    if (this.binding.userAccountTextInputLayout.getText() == "") {
                        // TODO: popup con error
                    } else {
                        hasAccount = true
                        user = this.binding.userAccountTextInputLayout.getText()
                    }
                }
            }

            var newClientData = ClientData(
                this.binding.cifTextInputLayout.getText(),
                this.binding.cityTextInputLayout.getText(),
                clientData.createdBy,
                this.binding.companyTextInputLayout.getText(),
                false,
                this.binding.directionTextInputLayout.getText(),
                this.binding.emailTextInputLayout.getText(),
                this.binding.emailTextInputLayout.getText(),
                hasAccount,
                clientData.id,
                listOf(
                    mapOf(this.binding.phoneTextInputLayoutName1.getText() to this.binding.phoneTextInputLayoutPhone1.getText().toLong()),
                    mapOf(this.binding.phoneTextInputLayoutName2.getText() to this.binding.phoneTextInputLayoutPhone2.getText().toLong()),
                ),
                this.binding.postalCodeTextInputLayout.getText().toLong(),
                this.binding.provinceTextInputLayout.getText(),
                null,
                user,
                clientData.documentId,
            )
            this.modifyClientViewModel.updateUser(newClientData)
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setFieldTexts() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        with(this.binding) {
            companyTextInputLayout.setInputText(clientData.company)
            directionTextInputLayout.setInputText(clientData.direction)
            cityTextInputLayout.setInputText(clientData.city)
            provinceTextInputLayout.setInputText(clientData.province)
            postalCodeTextInputLayout.setInputText(clientData.postalCode.toString())
            cifTextInputLayout.setInputText(clientData.cif)
            emailTextInputLayout.setInputText(clientData.email)
            phoneTextInputLayoutPhone1.setInputText(phone1.value.toString())
            phoneTextInputLayoutName1.setInputText(phone1.key)
            phoneTextInputLayoutPhone2.setInputText(phone2.value.toString())
            phoneTextInputLayoutName2.setInputText(phone2.key)

            userAccountTextInputLayout.isEnabled = checkedTextView.isChecked
            emailAccountTextInputLayout.isEnabled = checkedTextView.isChecked
        }
    }

    private fun setFieldTypes() {
        with(this.binding) {
            companyTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            directionTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            cityTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            provinceTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            postalCodeTextInputLayout.setInputType(InputType.TYPE_CLASS_NUMBER)
            cifTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
            emailTextInputLayout.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            phoneTextInputLayoutPhone1.setInputType(InputType.TYPE_CLASS_PHONE)
            phoneTextInputLayoutName1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            phoneTextInputLayoutPhone2.setInputType(InputType.TYPE_CLASS_PHONE)
            phoneTextInputLayoutName2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            emailAccountTextInputLayout.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        }
    }
}
