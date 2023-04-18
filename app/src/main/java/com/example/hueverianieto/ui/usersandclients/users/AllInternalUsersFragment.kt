package com.example.hueverianieto.ui.usersandclients.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.data.components.ComponentInternalUserModel
import com.example.hueverianieto.databinding.FragmentAllInternalUsersBinding
import com.example.hueverianieto.ui.usersandclients.clients.AllClientsActivity

class AllInternalUsersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllInternalUsersBinding

    private var internalUserList: MutableList<ComponentInternalUserModel> = mutableListOf()

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun configureUI() {
        this.binding.newInternalUserButton.isEnabled = true
        this.binding.newInternalUserButton.setText("Nuevo")
        this.binding.deletedInternalUsersButton.isEnabled = true
        this.binding.deletedInternalUsersButton.setText("Usuarios eliminados")
    }

    override fun setObservers() {
        // TODO: Sin implementar
    }

    override fun setListeners() {
        // TODO: Sin implementar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllInternalUsersActivity).configNav("Ver clientes")
        this.binding = FragmentAllInternalUsersBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

}