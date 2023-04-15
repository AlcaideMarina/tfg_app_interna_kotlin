package com.example.hueverianieto.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.MainActivity
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.data.bbdd.ClientData
import com.example.hueverianieto.data.components.ComponentClientModel
import com.example.hueverianieto.databinding.FragmentUsersAndClientsBinding
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.UserUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Investigar cómo hacer para que no se carguen todos los clientes de golpe, sino que sea según se vaya bajando

class UsersAndClientsFragment : BaseFragment() {

    private lateinit var binding: FragmentUsersAndClientsBinding
    private lateinit var view: View

    private val clientList: MutableList<ComponentClientModel?> = mutableListOf()

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

        getClientsListData()

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

    private fun getClientsListData() {
        val db = Firebase.firestore
        db.collection("client_info")
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
                            }
                        }
                        // Aquí ya tenemos toda la lista con todos los clientes
                    }

                } else {
                    // TODO
                    Log.e("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Error 1")
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Error 2")
            }
    }

}