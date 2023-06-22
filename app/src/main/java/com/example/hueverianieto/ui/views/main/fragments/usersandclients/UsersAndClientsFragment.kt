package com.example.hueverianieto.ui.views.main.fragments.usersandclients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersAndClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentUsersAndClientsBinding
    private lateinit var view: View
    private val usersAndClientsViewModel: UsersAndClientsViewModel by viewModels()
    private lateinit var internalUserData: InternalUserData

    override fun configureUI() {
        // Not necessary
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        this.binding.clientsButton.setOnClickListener {
            this.usersAndClientsViewModel
                .navigateToAllClientsActivity(requireContext(), internalUserData)
        }
        this.binding.internalUsersButton.setOnClickListener {
            this.usersAndClientsViewModel
                .navigateToAllInternalUsersActivity(requireContext(), internalUserData)
        }
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        internalUserData = (activity as MainActivity).currentUserData
        (activity as BaseActivity).changeTopBarName("Usuarios y clientes")
        this.binding = FragmentUsersAndClientsBinding
            .inflate(inflater, container, false)
        this.view = inflater.inflate(R.layout.fragment_users_and_clients, container, false)
        return this.binding.root
    }

}
