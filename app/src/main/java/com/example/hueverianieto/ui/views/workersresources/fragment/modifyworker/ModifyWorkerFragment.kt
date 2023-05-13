package com.example.hueverianieto.ui.views.workersresources.fragment.modifyworker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentWorkersDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail.WorkerDetailFragmentArgs
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyWorkerFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkersDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var internalUserData: InternalUserData
    private val modifyWorkerViewModel: ModifyWorkerViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        val args: ModifyWorkerFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        this.alertDialog = HNModalDialog(requireContext())

        this.binding = FragmentWorkersDetailBinding
            .inflate(inflater, container, false)

        return this.binding.root
    }

    override fun configureUI() {
        this.setTexts()
        this.setButtons()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            if (this.binding.salaryTextInputLayout.text != null) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Aviso",
                    "Esta acción cambiará el salario del trabajador con DNI ${this.internalUserData.dni}, pasando de ser de ${this.internalUserData.salary ?: "-"} € mensuales, a ${this.binding.salaryTextInputLayout.text}.\n¿Está seguro de que quiere continuar?",
                    "Atrás",
                    "Continuar",
                    { alertDialog.cancel() },
                    {
                        alertDialog.cancel()
                        val a =this.binding.salaryTextInputLayout.text
                        internalUserData.salary = this.binding.salaryTextInputLayout.text.toString().toDouble()
                        this.modifyWorkerViewModel.updateUser(internalUserData)
                    }
                )
            } else {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Introduzca un salario",
                    "Debe introducir el salario que asociar al trabajador. Por favor, revise los datos e inténtelo de nuevo.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }
        }
        //TODO botón cancelar
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setTexts() {
        with(this.binding) {
            this.idTextView.text = internalUserData.id.toString()
            this.nameTextView.text = internalUserData.name
            this.surnameTextView.text = internalUserData.surname
            this.dniTextView.text = internalUserData.dni
            this.accountTextView.text = internalUserData.bankAccount
            this.positionTextView.text = resources.getString(
                Utils.getKey(
                Constants.roles, internalUserData.position.toInt())!!)
            this.salaryTextInputLayout.setText((internalUserData.salary ?: "").toString())
            this.salaryTextInputLayout.isEnabled = true
        }
    }

    private fun setButtons() {
        with(this.binding) {
            this.saveButton.setText("Guardar")
            this.cancelButton.setText("Cancelar")
        }
    }

}
