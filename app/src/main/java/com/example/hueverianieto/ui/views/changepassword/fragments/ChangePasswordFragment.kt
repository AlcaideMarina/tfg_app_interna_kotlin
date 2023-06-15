package com.example.hueverianieto.ui.views.changepassword.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentChangePasswordBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.changepassword.ChangePasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment() {

    private lateinit var binding : FragmentChangePasswordBinding
    private lateinit var alertDialog: HNModalDialog

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
    }

    override fun setObservers() {
        // TODO
    }

    override fun setListeners() {
        // TODO
    }

    override fun updateUI(state: BaseState) {
        // TODO
    }

}