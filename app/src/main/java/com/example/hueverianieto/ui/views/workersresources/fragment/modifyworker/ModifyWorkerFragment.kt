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
        //TODO Botón guardar
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
