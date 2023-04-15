package com.example.hueverianieto.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.components.componentclientadapter.ComponentClientAdapter
import com.example.hueverianieto.data.components.ComponentClientModel
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding
import com.example.hueverianieto.utils.ClientUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentAllClientsBinding

    private val clientList: MutableList<ComponentClientModel> = mutableListOf()

    override fun injection() {
        // TODO: sin implementar
    }

    override fun configureUI() {
        this.binding.newUserButton.isEnabled = true
        this.binding.newUserButton.setText("Nuevos")
        this.binding.deletedUsersButton.isEnabled = true
        this.binding.deletedUsersButton.setText("Usuarios eliminados")

        getClientsListData()

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

    private fun getClientsListData() {
        val db = Firebase.firestore
        db.collection("client_info")
            .orderBy("id")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentList = task.result
                    if (!documentList.isEmpty) {
                        for (document in documentList) {
                            Log.v("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Consulta correcta")
                            val doc = document.data as MutableMap<String, Any?>?
                            if (ClientUtils.checkErrorMap(doc) == null) {
                                val data = doc as MutableMap<String, Any?>
                                val clientData = ClientUtils.mapToParcelable(data, document.id)
                                val componentClientModel = ComponentClientModel(
                                    clientData.id,
                                    clientData.company,
                                    clientData.cif
                                ) {
                                    // TODO: Navegación
                                }
                                clientList.add(componentClientModel)
                            } else {
                                // TODO
                                Log.e("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Error 1")
                            }
                        }
                        initRecyclerView()
                    } else {
                        // TODO: Mostrar un texto
                    }

                } else {
                    // TODO
                    Log.e("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Error 2")
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Error 3")
            }
    }

    private fun initRecyclerView() {
        this.binding.clientsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.clientsRecyclerView.adapter = ComponentClientAdapter(clientList)
    }

}