package com.example.hueverianieto.ui.views.clients.fragments.deletedclients

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
import com.example.hueverianieto.databinding.FragmentDeletedClientsBinding
import com.example.hueverianieto.domain.model.componentclient.ComponentClientModel
import com.example.hueverianieto.ui.components.componentclientadapter.ComponentClientAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeletedClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentDeletedClientsBinding
    private var clientList: MutableList<ComponentClientModel> = mutableListOf()
    private val deletedClientsViewModel: DeletedClientsViewModel by viewModels()
    private lateinit var currentUserData: InternalUserData

    override fun configureUI() {
        this.deletedClientsViewModel.getClientsListData()
        lifecycleScope.launchWhenStarted {
            deletedClientsViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        deletedClientsViewModel.clientList.observe(this) { clientDataList ->
            if (clientDataList == null) {
                this.binding.clientsRecyclerView.visibility = View.GONE
                this.binding.containerWaringNoClients.visibility = View.VISIBLE
                this.binding.containerWaringNoClients.setTitle("Error")
                this.binding.containerWaringNoClients.setText("Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.")
            } else {
                clientList = mutableListOf()
                for (clientData in clientDataList) {
                    if (clientData != null) {
                        val componentClientModel = ComponentClientModel(
                            clientData.id.toString(),
                            clientData.company,
                            clientData.cif,
                            null
                        )
                        clientList.add(componentClientModel)
                    }
                }
                if (clientList.isEmpty()) {
                    this.binding.clientsRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoClients.visibility = View.VISIBLE
                    this.binding.containerWaringNoClients.setTitle("No hay clientes")
                    this.binding.containerWaringNoClients.setText("No hay registro de clientes eliminados en la base de datos")
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
            with(state as DeletedClientsViewState) {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentDeletedClientsBinding
            .inflate(inflater, container, false)

        val args: DeletedClientsFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        return this.binding.root
    }

    private fun initRecyclerView() {
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientAdapter(clientList)
        this.binding.clientsRecyclerView.setHasFixedSize(false)
    }

    companion object {
        private val TAG = DeletedClientsFragment::class.java.simpleName
    }

}
