package com.example.hueverianieto.ui.views.clients.fragments.allclients

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
import com.example.hueverianieto.ui.components.componentclientadapter.ComponentClientAdapter
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.model.componentclient.ComponentClientModel
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.main.fragments.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentAllClientsBinding
    private lateinit var currentUserData: InternalUserData

    private var clientList: MutableList<ComponentClientModel> = mutableListOf()
    private val allClientsViewModel : AllClientsViewModel by viewModels()


    override fun configureUI() {

        this.binding.newUserButton.isEnabled = true
        this.binding.newUserButton.setText("Nuevo")
        this.binding.deletedUsersButton.isEnabled = true
        this.binding.deletedUsersButton.setText("Clientes eliminados")

        this.allClientsViewModel.getClientsData()
        lifecycleScope.launchWhenStarted {
            allClientsViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        allClientsViewModel.clientList.observe(this) { clientDataList ->
            if (clientDataList == null) {
                // TODO: ERROR
            } else {
                clientList = mutableListOf()
                for (clientData in clientDataList)  {
                    if (clientData != null) {
                        val componentClientModel = ComponentClientModel(
                            clientData.id.toString(),
                            clientData.company,
                            clientData.cif
                        ) {
                            this.allClientsViewModel.navigateToClientDetails(
                                this.view,
                                bundleOf(
                                    "clientData" to clientData,
                                    "currentUserData" to currentUserData
                                )
                            )
                        }
                        clientList.add(componentClientModel)
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
        this.binding.newUserButton.setOnClickListener {
            this.allClientsViewModel.navigateToNewClient(
                this.view,
                bundleOf("currentUserData" to currentUserData)
            )
        }
        this.binding.deletedUsersButton.setOnClickListener {
            this.allClientsViewModel.navigateDeleteClients(
                this.view,
                bundleOf("currentUserData" to currentUserData)
            )
        }
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as AllClientsViewState) {
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
        (activity as AllClientsActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as AllClientsActivity).currentUserData

        this.binding = FragmentAllClientsBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    private fun getClientsListData() {
        val db = Firebase.firestore
        db.collection("client_info")
            .orderBy("id")
            .get()
            .addOnCompleteListener { task ->
                clientList = mutableListOf()
                if (task.isSuccessful) {
                    val documentList = task.result
                    if (!documentList.isEmpty) {
                        this.binding.clientsRecyclerView.visibility = View.VISIBLE
                        this.binding.containerWaringNoClients.visibility = View.GONE
                        for (document in documentList) {
                            Log.v("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Consulta correcta")
                            val doc = document.data as MutableMap<String, Any?>?
                            if (ClientUtils.checkErrorMap(doc) == null) {
                                val data = doc as MutableMap<String, Any?>
                                val clientData = ClientUtils.mapToParcelable(data, document.id)
                                if (!clientData.deleted) {
                                    val componentClientModel = ComponentClientModel(
                                        clientData.id.toString(),
                                        clientData.company,
                                        clientData.cif
                                    ) {
                                        this.allClientsViewModel.navigateToClientDetails(
                                            this.view,
                                            bundleOf("clientData" to clientData)
                                        )
                                    }
                                    clientList.add(componentClientModel)
                                }
                            } else {
                                // TODO
                                Log.e("CONSULTA", AllClientsFragment::class.java.simpleName + " - Error 1")
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
                    } else {
                        this.binding.clientsRecyclerView.visibility = View.GONE
                        this.binding.containerWaringNoClients.visibility = View.VISIBLE
                        this.binding.containerWaringNoClients.setTitle("No hay clientes")
                        this.binding.containerWaringNoClients.setText("No hay registro de clientes activos en la base de datos")
                    }

                } else {
                    // TODO
                    Log.e("CONSULTA", AllClientsFragment::class.java.simpleName + " - Error 2")
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", AllClientsFragment::class.java.simpleName + " - Error 3")
            }
    }

    private fun initRecyclerView() {
        this.binding.clientsRecyclerView.visibility = View.VISIBLE
        this.binding.containerWaringNoClients.visibility = View.GONE
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientAdapter(clientList)
        this.binding.clientsRecyclerView.setHasFixedSize(false)
    }

    companion object {
        private val TAG = AllClientsFragment::class.java.simpleName
    }

}
