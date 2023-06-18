package com.example.hueverianieto.ui.views.internalusers.fragments.deletedinternalusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentDeletedInternalUsersBinding
import com.example.hueverianieto.domain.model.componentinternaluser.ComponentInternalUserModel
import com.example.hueverianieto.ui.components.componentinternaluseradapter.ComponentInternalUserAdapter
import com.example.hueverianieto.ui.views.clients.fragments.deletedclients.DeletedClientsFragmentArgs
import com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers.AllInternalUsersFragment
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeletedInternalUsersFragment : BaseFragment() {

    private lateinit var binding: FragmentDeletedInternalUsersBinding
    private lateinit var currentUserData: InternalUserData

    private var internalUserList: MutableList<ComponentInternalUserModel> = mutableListOf()
    private val deletedInternalUsersViewModel: DeletedInternalUsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: DeletedClientsFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentDeletedInternalUsersBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.deletedInternalUsersViewModel.getInternalUserData()
        lifecycleScope.launchWhenStarted {
            deletedInternalUsersViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        deletedInternalUsersViewModel.internalUserList.observe(this) { internalUserDataList ->
            if (internalUserDataList == null) {
                this.binding.deletedInternalUsersRecyclerView.visibility = View.GONE
                this.binding.containerWaringNoDeletedInternalUsers.visibility = View.VISIBLE
                this.binding.containerWaringNoDeletedInternalUsers.setTitle("Error")
                this.binding.containerWaringNoDeletedInternalUsers.setText("Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.")
            } else {
                internalUserList = mutableListOf()
                for (internalUserData in internalUserDataList) {
                    if (internalUserData != null) {
                        val componentInternalUserModel = ComponentInternalUserModel(
                            internalUserData.id.toString(),
                            internalUserData.name,
                            internalUserData.surname,
                            internalUserData.dni,
                            requireContext().getString(
                                Utils.getKey(
                                    Constants.roles,
                                    internalUserData.position.toInt()
                                )!!
                            ),
                            null
                        )
                        internalUserList.add(componentInternalUserModel)
                    }
                }
                if (internalUserList.isEmpty()) {
                    this.binding.deletedInternalUsersRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoDeletedInternalUsers.visibility = View.VISIBLE
                    this.binding.containerWaringNoDeletedInternalUsers.setTitle("No hay usuarios internos")
                    this.binding.containerWaringNoDeletedInternalUsers.setText("No hay registro de usuarios internos eliminados en la base de datos.")
                } else {
                    initRecyclerView()
                }
            }
        }
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as DeletedInternalUsersViewState) {
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
        this.binding.deletedInternalUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.deletedInternalUsersRecyclerView.adapter =
            ComponentInternalUserAdapter(internalUserList)
        this.binding.deletedInternalUsersRecyclerView.setHasFixedSize(false)
    }

    private fun navigateToInternalUserDetails() {
        // TODO: Navegación a pantalla de detalles
        Log.v(
            "NAVEGACIÓN",
            DeletedInternalUsersFragment::class.java.simpleName + " - Aquí iría la navegación "
        )
    }


    companion object {
        private val TAG = AllInternalUsersFragment::class.java.simpleName
    }

}