package com.example.hueverianieto.ui.views.usersandclients

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding
import com.example.hueverianieto.ui.views.usersandclients.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.usersandclients.users.AllInternalUsersActivity

// TODO: Investigar cómo hacer para que no se carguen todos los clientes de golpe, sino que sea según se vaya bajando

class UsersAndClientsFragment : BaseFragment() {

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
        this.binding.clientsButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, AllClientsActivity::class.java)
                it.startActivity(intent)
            } ?: Log.e(
                UsersAndClientsFragment::class.simpleName,
                "Error en la navegación en clientsButton"
            )
        }
        this.binding.internalUsersButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, AllInternalUsersActivity::class.java)
                it.startActivity(intent)
            } ?: Log.e(
                UsersAndClientsFragment::class.simpleName,
                "Error en la navegación en internalUsersButton"
            )
        }
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