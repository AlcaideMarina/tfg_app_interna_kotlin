package com.example.hueverianieto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding

class AllClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentAllClientsBinding

    override fun injection() {
        // TODO: sin implementar
    }

    override fun configureUI() {
        this.binding.newUserButton.isEnabled = true
        this.binding.newUserButton.setText("Nuevos")
        this.binding.deletedUsersButton.isEnabled = true
        this.binding.deletedUsersButton.setText("Usuarios eliminados")
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
        this.binding = FragmentAllClientsBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

}