package com.example.hueverianieto.ui.views.changepassword.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentChangePasswordBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.changepassword.ChangePasswordActivity
import com.example.hueverianieto.utils.Utils.setPopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private lateinit var alertDialog: HNModalDialog
    private val changePasswordViewModel : ChangePasswordViewModel by viewModels()

    lateinit var currentUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as ChangePasswordActivity).configNav(true)
        this.binding = FragmentChangePasswordBinding.inflate(
            inflater, container, false
        )

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root
    }

    override fun configureUI() {
        this.binding.saveButton.setText("Cambiar contraseña")
        lifecycleScope.launchWhenStarted {
            changePasswordViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.changePasswordViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 0) {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            (activity as ChangePasswordActivity).goBackFragments()
                        },
                        null
                    )
                } else {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null,
                    )
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            if (this.binding.oldPasswordTextInputLayout.text != null && this.binding.oldPasswordTextInputLayout.text.toString() != ""
                && this.binding.newPassword1TextInputLayout.text != null && this.binding.newPassword1TextInputLayout.text.toString() != ""
                && this.binding.newPassword2TextInputLayout.text != null && this.binding.newPassword2TextInputLayout.text.toString() != "") {
                val oldPass = this.binding.oldPasswordTextInputLayout.text.toString()
                val newPass1 = this.binding.newPassword1TextInputLayout.text.toString()
                val newPass2 = this.binding.newPassword2TextInputLayout.text.toString()
                if (newPass1 == newPass2) {
                    this.changePasswordViewModel
                        .checkOldPassword(
                            oldPass,
                            newPass1
                        )
                } else  {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        "Las contraseñas no coinciden",
                        "El campo de repetición de contraseña debe ser exactamente igual " +
                                "que el de 'Nueva contraseña'. Por favor, revise los datos e inténtelo de nuevo.",
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null,
                    )
                }
            } else {
                setPopUp(
                    alertDialog,
                    requireContext(),
                    "Formulario incompleto",
                    "Debe rellenar todos los campos del formulario. Por favor revise los datos e inténtelo de nuevo.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null,
                )
            }
        }
    }

    override fun updateUI(state: BaseState) {
        state as ChangePasswordViewState
        this.binding.loadingComponent.isVisible = state.isLoading
    }

}