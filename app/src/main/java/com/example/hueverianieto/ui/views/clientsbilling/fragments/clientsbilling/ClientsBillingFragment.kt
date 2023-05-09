package com.example.hueverianieto.ui.views.clientsbilling.fragments.clientsbilling

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.databinding.FragmentClientsBillingBinding
import com.example.hueverianieto.domain.model.componentclient.ComponentClientModel
import com.example.hueverianieto.domain.model.componentclientmodel.ComponentClientBillingModel
import com.example.hueverianieto.ui.components.componentclientadapter.ComponentClientAdapter
import com.example.hueverianieto.ui.components.componentclientbilling.ComponentClientBillingAdapter
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.clients.fragments.allclients.AllClientsFragment
import com.example.hueverianieto.ui.views.clients.fragments.allclients.AllClientsViewState
import com.example.hueverianieto.ui.views.clients.fragments.deletedclients.DeletedClientsViewModel
import com.example.hueverianieto.ui.views.clientsbilling.ClientsBillingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientsBillingFragment : BaseFragment() {

    private lateinit var binding: FragmentClientsBillingBinding
    private var clientList: MutableList<ComponentClientBillingModel> = mutableListOf()
    private val clientsBillingViewModel : ClientsBillingViewModel by viewModels()
    private lateinit var currentUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as ClientsBillingActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as ClientsBillingActivity).currentUserData

        this.binding = FragmentClientsBillingBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    override fun configureUI() {
        this.clientsBillingViewModel.getClientsData()
        lifecycleScope.launchWhenStarted {
            clientsBillingViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        clientsBillingViewModel.clientList.observe(this) { clientDataList ->
            if (clientDataList == null) {
                // TODO: ERROR
            } else {
                clientList = mutableListOf()
                for (clientData in clientDataList)  {
                    if (clientData != null) {
                        val componentBillingClientModel = ComponentClientBillingModel(
                            clientData.id.toString(),
                            clientData.company,
                        ) {
                            /*this.clientsBillingViewModel.navigateToClientDetails(
                                this.view,
                                bundleOf(
                                    "clientData" to clientData,
                                    "currentUserData" to currentUserData
                                )
                            )*/
                        }
                        clientList.add(componentBillingClientModel)
                    }
                }
                if (clientList.isEmpty()) {
                    this.binding.clientsRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoClients.visibility = View.VISIBLE
                    this.binding.containerWaringNoClients.setTitle("No hay clientes")
                    this.binding.containerWaringNoClients.setText("No hay registro de clientes activos en la base de datos")
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
            with(state as ClientsBillingViewState) {
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
        this.binding.clientsRecyclerView.visibility = View.VISIBLE
        this.binding.containerWaringNoClients.visibility = View.GONE
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientBillingAdapter(clientList)
        this.binding.clientsRecyclerView.setHasFixedSize(false)
    }

    companion object {
        private val TAG = ClientsBillingFragment::class.java.simpleName
    }


}