package com.example.hueverianieto.ui.views.internalusers.fragments.modifyinternaluser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifyInternalUserBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import com.example.hueverianieto.utils.Utils.setPopUp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ModifyInternalUserFragment : BaseFragment() {

    private lateinit var binding : FragmentModifyInternalUserBinding
    private lateinit var currentUserData : InternalUserData
    private lateinit var internalUserData : InternalUserData
    private var dropdownRoleItems : MutableList<String> = mutableListOf()
    private val modifyInternalUserViewModel: ModifyInternalUserViewModel by viewModels()
    private lateinit var alertDialog: HNModalDialog

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

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        setFieldText()
        setDropdownRoleOptions()

        lifecycleScope.launchWhenStarted {
            modifyInternalUserViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.modifyInternalUserViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 1) {
                    setPopUp(
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
                    setPopUp(
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
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            val positionSelected : Int? = when (this.binding.roleAutoCompleteTextView.text.toString()) {
                requireContext().getString(R.string.warehouse_job) -> R.string.warehouse_job
                requireContext().getString(R.string.boss_job) -> R.string.boss_job
                requireContext().getString(R.string.farm_job) -> R.string.farm_job
                requireContext().getString(R.string.office_job) -> R.string.office_job
                requireContext().getString(R.string.delivery_job) -> R.string.delivery_job
                else -> null
            }
            val internalUserData = InternalUserData(
                this.binding.bankAccountTextInputLayout.text.toString(),
                this.binding.cityTextInputLayout.text.toString(),
                this.internalUserData.createdBy,
                this.internalUserData.deleted,
                this.binding.directionTextInputLayout.text.toString(),
                this.binding.dniTextInputLayout.text.toString(),
                this.binding.emailTextInputLayout.text.toString(),
                this.internalUserData.id,
                this.binding.nameTextInputLayout.text.toString(),
                this.binding.phoneTextInputLayout.text.toString().toLong(),
                Constants.roles[positionSelected]!!.toLong(),
                this.binding.postalCodeTextInputLayout.text.toString().toLong(),
                this.binding.provinceTextInputLayout.text.toString(),
                this.internalUserData.salary,
                this.binding.ssNumberTextInputLayout.text.toString().toLong(),
                this.binding.surnameTextInputLayout.text.toString(),
                this.internalUserData.uid,
                this.binding.userAccountTextInputLayout.text.toString(),
                this.internalUserData.documentId
            )
            this.modifyInternalUserViewModel.updateUser(internalUserData)
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifyInternalUserViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
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
            roleAutoCompleteTextView.setText(
                Utils.getKey(Constants.roles, internalUserData.position.toInt())!!
            )
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
