package com.example.hueverianieto.ui.views.clients.fragments.newclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewClientFragment : BaseFragment() {

    private lateinit var binding: FragmentNewClientBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var currentUserData: InternalUserData
    private val newClientViewModel: NewClientViewModel by viewModels()

    private var company: String = ""
    private var direction: String = ""
    private var city: String = ""
    private var province: String = ""
    private var postalCode: String = ""
    private var cif: String = ""
    private var email: String = ""
    private var phone1: String = ""
    private var namePhone1: String = ""
    private var phone2: String = ""
    private var namePhone2: String = ""
    private var hasAccount: Boolean = false
    private var accountUser: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentNewClientBinding.inflate(
            inflater, container, false
        )

        val args: NewClientFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        return this.binding.root
    }

    override fun configureUI() {

        this.alertDialog = HNModalDialog(requireContext())

        this.binding.let {
            it.saveButtonText.text = "Guardar"
            it.cancelButtonText.text = "Cancelar"

            it.userAccountTextInputLayout.isEnabled = false

        }

        lifecycleScope.launchWhenStarted {
            newClientViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newClientViewModel.alertDialog.observe(this) { alertOkData ->
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
                    setPopUp(alertOkData.title, alertOkData.text) { alertDialog.cancel() }
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.checkedTextView.setOnClickListener {
            this.binding.checkedTextView.isChecked = !this.binding.checkedTextView.isChecked
            this.binding.userAccountTextInputLayout.isEnabled =
                this.binding.checkedTextView.isChecked
        }

        this.binding.cancelButton.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        this.binding.saveButton.setOnClickListener { checkInfo() }
    }

    override fun updateUI(state: BaseState) {
        with(state as NewClientViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setPopUp(title: String, message: String, listener: OnClickListener) {
        alertDialog.show(
            requireContext(),
            ModalDialogModel(
                title,
                message,
                "De acuerdo",
                null,
                listener,
                null,
                true
            )
        )
    }

    private fun checkInfo() {
        variableAssignations()
        if (company != "" && direction != "" && city != "" && province != "" && postalCode.toLongOrNull() != null &&
            cif != "" && email != "" && phone1.toLongOrNull() != null && namePhone1 != "" && phone2.toLongOrNull() != null &&
            namePhone2 != ""
        ) {
            val clientData = ClientData(
                cif,
                city,
                "user_${currentUserData.id}",
                company,
                false,
                direction,
                email,
                hasAccount,
                null,
                listOf(
                    mapOf(namePhone1 to phone1.toLong()),
                    mapOf(namePhone2 to phone2.toLong())
                ),
                postalCode.toLong(),
                province,
                null,
                accountUser,
                null
            )
            this.newClientViewModel.addNewClient(clientData)
        } else {
            setPopUp(
                "Formulario incompleto",
                "Debe rellenar todos los campos del formulario. Por favor revise los datos e inténtelo de nuevo."
            ) {
                alertDialog.cancel()
            }
        }
    }

    private fun variableAssignations() {
        this.binding.let {
            company = it.companyTextInputLayout.text.toString()
            direction = it.directionTextInputLayout.text.toString()
            city = it.cityTextInputLayout.text.toString()
            province = it.provinceTextInputLayout.text.toString()
            postalCode = it.postalCodeTextInputLayout.text.toString()
            cif = it.cifTextInputLayout.text.toString()
            email = it.emailTextInputLayout.text.toString()
            phone1 = it.phoneTextInputLayoutPhone1.text.toString()
            namePhone1 = it.phoneTextInputLayoutName1.text.toString()
            phone2 = it.phoneTextInputLayoutPhone2.text.toString()
            namePhone2 = it.phoneTextInputLayoutName2.text.toString()
            hasAccount = it.checkedTextView.isChecked
            accountUser = if (hasAccount) it.userAccountTextInputLayout.text.toString() else null
        }
    }

}
