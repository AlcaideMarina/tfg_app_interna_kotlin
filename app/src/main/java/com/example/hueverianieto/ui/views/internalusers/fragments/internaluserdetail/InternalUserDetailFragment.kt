package com.example.hueverianieto.ui.views.internalusers.fragments.internaluserdetail

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
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentInternalUserDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InternalUserDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentInternalUserDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var internalUserData: InternalUserData
    private val internalUserDetailViewModel: InternalUserDetailViewModel by viewModels()
    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentInternalUserDetailBinding.inflate(
            inflater, container, false
        )

        val args: InternalUserDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root
    }

    override fun configureUI() {

        this.internalUserDetailViewModel.getInternalUserData(internalUserData.documentId!!)

        setButtons()
        setFieldText()

        lifecycleScope.launchWhenStarted {
            internalUserDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.internalUserDetailViewModel.alertDialog.observe(this) { alertErrorOkData ->
            if (alertErrorOkData.finish) {
                if (alertErrorOkData.customCode == 0) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertErrorOkData.title,
                        alertErrorOkData.text,
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
                        alertErrorOkData.title,
                        alertErrorOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
        this.internalUserDetailViewModel.internalUserData.observe(this) {
            this.internalUserData = it
            setFieldText()
        }
    }

    override fun setListeners() {
        this.binding.deleteUserButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. ¿Está seguro de que quiere eliminar el usuario?",
                "Cancelar",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    this.internalUserDetailViewModel.deleteInternalUser(
                        internalUserData.documentId!!
                    )
                }
            )
        }
        this.binding.modifyButton.setOnClickListener {
            this.internalUserDetailViewModel.navigateToModifyInternalUser(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "internalUserData" to internalUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as InternalUserDetailViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        this.binding.modifyButtonText.text = "Modificar"
        this.binding.deleteUserButtonText.text = "Eliminar usuario"
        if (currentUserData.documentId == internalUserData.documentId) {
            this.binding.deleteUserButtonText.isEnabled = false
        }
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
            roleAutoCompleteTextView.setText(
                Utils.getKey(Constants.roles, internalUserData.position.toInt())!!
            )
            roleAutoCompleteTextView.isEnabled = false
            roleTextInputLayout.isEnabled = false
            userAccountTextInputLayout.setText(internalUserData.user)
            userAccountTextInputLayout.isEnabled = false
        }
    }

}
