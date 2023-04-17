package com.example.hueverianieto.ui.usersandclients.clients

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.components.componentclientadapter.ComponentClientAdapter
import com.example.hueverianieto.data.components.ComponentClientModel
import com.example.hueverianieto.databinding.FragmentDeletedClientsBinding
import com.example.hueverianieto.ui.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeletedClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentDeletedClientsBinding

    private var clientList: MutableList<ComponentClientModel> = mutableListOf()

    override fun injection() {
        // TODO: sin implementar
    }

    override fun configureUI() {

        getClientsListData()

    }

    override fun setObservers() {
        // TODO: sin implementar
    }

    override fun setListeners() {
        //
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllClientsActivity).configNav("Ver clientes eliminados")
        this.binding = FragmentDeletedClientsBinding
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
                                if (clientData.deleted) {
                                    val componentClientModel = ComponentClientModel(
                                        clientData.id,
                                        clientData.company,
                                        clientData.cif
                                    ) {
                                        navigateToClientDetails()
                                    }
                                    clientList.add(componentClientModel)
                                }
                            } else {
                                // TODO
                                Log.e("CONSULTA", DeletedClientsFragment::class.java.simpleName + " - Error 1")
                            }
                        }
                        if (clientList.isEmpty()) {
                            this.binding.clientsRecyclerView.visibility = View.GONE
                            this.binding.containerWaringNoClients.visibility = View.VISIBLE
                            this.binding.containerWaringNoClients.setTitle("No hay clientes eliminados")
                            this.binding.containerWaringNoClients.setText("No hay registro de clientes eliminados en la base de datos")
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
                    Log.e("CONSULTA", DeletedClientsFragment::class.java.simpleName + " - Error 2")
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", DeletedClientsFragment::class.java.simpleName + " - Error 3")
            }
    }

    private fun initRecyclerView() {
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientAdapter(clientList)
        this.binding.clientsRecyclerView.setHasFixedSize(false)
    }

    private fun navigateToClientDetails() {
        // TODO: Navegación a pantalla de detalles
        Log.v("NAVEGACIÓN", DeletedClientsFragment::class.java.simpleName + " - Aquí iría la navegación ")
    }

}