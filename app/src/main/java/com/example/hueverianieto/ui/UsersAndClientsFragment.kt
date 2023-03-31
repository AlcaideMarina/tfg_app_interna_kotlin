package com.example.hueverianieto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding

class UsersAndClientsFragment: BaseFragment() {

    private lateinit var binding: FragmentUsersAndClientsBinding
    private lateinit var view: View

    override fun injection() {
        // TODO: sin implementar
    }

    override fun configureUI() {
        this.binding.clientsButton.isEnabled = true
        this.binding.clientsButton.setText("Ver clientes")
        this.binding.internalUsersButton.isEnabled = true
        this.binding.internalUsersButton.setText("Usuarios internos")
        this.binding.externalUsersButton.isEnabled = true
        this.binding.externalUsersButton.setText("Usuarios externos")
    }

    override fun setObservers() {
        // TODO: sin implementar
    }

    override fun setListeners() {
        // TODO: sin implementar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentUsersAndClientsBinding
            .inflate(inflater, container, false)
        this.view = inflater.inflate(R.layout.fragment_users_and_clients, container, false)
        return this.binding.root
    }

}