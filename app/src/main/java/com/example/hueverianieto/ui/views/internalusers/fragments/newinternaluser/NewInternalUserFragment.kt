package com.example.hueverianieto.ui.views.internalusers.fragments.newinternaluser

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
import com.example.hueverianieto.databinding.FragmentNewInternalUserBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewInternalUserFragment : BaseFragment() {

    private lateinit var binding: FragmentNewInternalUserBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var currentUserData: InternalUserData
    private var dropdownRoleItems: MutableList<String> = mutableListOf()
    private val newInternalUserViewModel: NewInternalUserViewModel by viewModels()

    var name: String = ""
    var surname: String = ""
    var dni: String = ""
    var phone: String = ""
    var email: String = ""
    var direction: String = ""
    var city: String = ""
    var province: String = ""
    var postalCode: String = ""
    var salary: String = ""
    var ssNumber: String = ""
    var bankAccount: String = ""
    var position: String = ""
    var user: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: NewInternalUserFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewInternalUserBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {

        setButtons()
        setDropdownRoleOptions()
        this.alertDialog = HNModalDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            newInternalUserViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

    }

    override fun setObservers() {
        this.newInternalUserViewModel.alertDialog.observe(this) { alertOkData ->
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
                            activity?.onBackPressedDispatcher?.onBackPressed()
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
        this.binding.roleAutoCompleteTextView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.hideSoftInput()
            }
        }
        this.binding.cancelButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        this.binding.saveButton.setOnClickListener {
            checkFields()
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as NewInternalUserViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        this.binding.saveButtonText.text = "Guardar"
        this.binding.cancelButtonText.text = "Cancelar"
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

    private fun checkFields() {
        variableAssignations()
        if (name.isNotEmpty() && surname.isNotEmpty() && dni.isNotEmpty() && phone.isNotEmpty() &&
            email.isNotEmpty() && direction.isNotEmpty() && city.isNotEmpty() &&
            province.isNotEmpty() && postalCode.isNotEmpty() && ssNumber.isNotEmpty() &&
            bankAccount.isNotEmpty() && position.isNotEmpty() && user.isNotEmpty()
        ) {

            val positionSelected: Int? = when (position) {
                requireContext().getString(R.string.warehouse_job) -> R.string.warehouse_job
                requireContext().getString(R.string.boss_job) -> R.string.boss_job
                requireContext().getString(R.string.farm_job) -> R.string.farm_job
                requireContext().getString(R.string.office_job) -> R.string.office_job
                requireContext().getString(R.string.delivery_job) -> R.string.delivery_job
                else -> null
            }

            // Crear objeto
            val internalUserData = InternalUserData(
                bankAccount,
                city,
                "user_${currentUserData.id}",
                false,
                direction,
                dni,
                email,
                null,
                name,
                phone.toLong(),
                Constants.roles[positionSelected]!!.toLong(),
                postalCode.toLong(),
                province,
                null,
                ssNumber.toLong(),
                surname,
                null,
                user,
                null
            )
            // llamar a la función del VM
            this.newInternalUserViewModel.addNewInternalUser(internalUserData)
        } else {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Formulario incompleto",
                "Debe rellenar todos los campos del formulario. Por favor revise los datos e inténtelo de nuevo.",
                "De acuerdo",
                null,
                { alertDialog.cancel() },
                null
            )
        }
    }

    private fun variableAssignations() {
        with(this.binding) {
            name = nameTextInputLayout.text.toString()
            surname = surnameTextInputLayout.text.toString()
            dni = dniTextInputLayout.text.toString()
            phone = phoneTextInputLayout.text.toString()
            email = emailTextInputLayout.text.toString()
            direction = directionTextInputLayout.text.toString()
            city = cityTextInputLayout.text.toString()
            province = provinceTextInputLayout.text.toString()
            postalCode = postalCodeTextInputLayout.text.toString()
            ssNumber = ssNumberTextInputLayout.text.toString()
            bankAccount = bankAccountTextInputLayout.text.toString()
            position = roleAutoCompleteTextView.text.toString()
            user = userAccountTextInputLayout.text.toString()
        }
    }

}
