package com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.ui.components.componentinternaluseradapter.ComponentInternalUserAdapter
import com.example.hueverianieto.domain.model.componentinternaluser.ComponentInternalUserModel
import com.example.hueverianieto.databinding.FragmentAllInternalUsersBinding
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.main.fragments.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.ui.views.clients.fragments.allclients.AllClientsFragment
import com.example.hueverianieto.ui.views.clients.fragments.allclients.AllClientsViewState
import com.example.hueverianieto.ui.views.internalusers.AllInternalUsersActivity
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AllInternalUsersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllInternalUsersBinding
    private lateinit var currentUserData: InternalUserData

    private var internalUserList: MutableList<ComponentInternalUserModel> = mutableListOf()
    private val allInternalUsersViewModel : AllInternalUsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as AllInternalUsersActivity).getToolbar()
            .setNavigationOnClickListener { (activity as BaseActivity).goBackFragments() }

        currentUserData = (activity as AllInternalUsersActivity).currentUserData

        this.binding = FragmentAllInternalUsersBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    override fun configureUI() {
        this.binding.newInternalUserButton.setText("Nuevo")
        this.binding.deletedInternalUsersButton.setText("Usuarios eliminados")
        this.allInternalUsersViewModel.getInternalUserData()
        lifecycleScope.launchWhenStarted {
            allInternalUsersViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        allInternalUsersViewModel.internalUserList.observe(this) { internalUserDataList ->
            if (internalUserDataList == null) {
                // TODO: ERROR
            } else {
                internalUserList = mutableListOf()
                for (internalUserData in internalUserDataList) {
                    if (internalUserData != null) {
                        val componentInternalUserModel = ComponentInternalUserModel(
                            internalUserData.id.toString(),
                            internalUserData.name,
                            internalUserData.surname,
                            internalUserData.dni,
                            "internalUserData.role"
                        ) {
                            // TODO
                            Log.v("NAVEGACIÓN", AllInternalUsersFragment::class.java.simpleName)
                        }
                        internalUserList.add(componentInternalUserModel)
                    }
                }
                if (internalUserList.isEmpty()) {
                    this.binding.internalUsersRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoInternalUsers.visibility = View.VISIBLE
                    this.binding.containerWaringNoInternalUsers.setTitle("No hay usuarios internos")
                    this.binding.containerWaringNoInternalUsers.setText("No hay registro de usuarios internos en la base de datos")
                } else {
                    initRecyclerView()
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.deletedInternalUsersButton.setOnClickListener {
            this.allInternalUsersViewModel.navigateDeleteInternalUsers(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData
                )
            )
        }
        this.binding.newInternalUserButton.setOnClickListener {
            this.allInternalUsersViewModel.navigateToNewInternalUsers(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as AllInternalUsersViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    if (state.error) {
                        // TODO: Popup error
                    } else if (state.isEmpty) {
                        // TODO: Popup está vacío
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString(), e)
        }
    }

    private fun initRecyclerView() {
        this.binding.internalUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.internalUsersRecyclerView.adapter = ComponentInternalUserAdapter(internalUserList)
        this.binding.internalUsersRecyclerView.setHasFixedSize(false)
    }

    companion object {
        private val TAG = AllInternalUsersFragment::class.java.simpleName
    }

}
