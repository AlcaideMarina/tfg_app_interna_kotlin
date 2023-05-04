package com.example.hueverianieto.ui.views.usersandclients.clients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.ui.components.componentclientadapter.ComponentClientAdapter
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.model.componentclient.ComponentClientModel
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.ui.views.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentAllClientsBinding

    private var clientList: MutableList<ComponentClientModel> = mutableListOf()


    override fun configureUI() {

        this.binding.newUserButton.isEnabled = true
        this.binding.newUserButton.setText("Nuevo")
        this.binding.deletedUsersButton.isEnabled = true
        this.binding.deletedUsersButton.setText("Clientes eliminados")

        getClientsListData()

    }

    override fun setObservers() {
        // TODO: sin implementar
    }

    override fun setListeners() {
        this.binding.newUserButton.setOnClickListener { navigateToNewClient() }
        this.binding.deletedUsersButton.setOnClickListener { navigateDeleteClients() }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as AllClientsActivity).getToolbar().setNavigationOnClickListener { (activity as BaseActivity).goBackFragments() }
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
                                        clientData.id,
                                        clientData.company,
                                        clientData.cif
                                    ) {
                                        navigateToClientDetails(clientData)
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
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientAdapter(clientList)
        this.binding.clientsRecyclerView.setHasFixedSize(false)
    }

    private fun navigateToClientDetails(clientData: ClientData) {
        this.view?.findNavController()?.navigate(R.id.action_allClientsFragment_to_clientDetailFragment, bundleOf("clientData" to clientData))
            ?: Log.e(
                AllClientsFragment::class.simpleName,
                "Error en la navegación en newClientButton"
            )
    }

    private fun navigateToNewClient() {
        this.view?.findNavController()?.navigate(R.id.action_allClientsFragment_to_newClientFragment)
            ?: Log.e(
            AllClientsFragment::class.simpleName,
            "Error en la navegación en newClientButton"
        )
    }

    private fun navigateDeleteClients() {
        this.view?.findNavController()?.navigate(R.id.action_allClientsFragment_to_deletedClientsFragment)
            ?: Log.e(
                AllClientsFragment::class.simpleName,
                "Error en la navegación en newClientButton"
            )
    }

}
