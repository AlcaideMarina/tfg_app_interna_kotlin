package com.example.hueverianieto.ui.views.usersandclients.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.ui.components.componentinternaluseradapter.ComponentInternalUserAdapter
import com.example.hueverianieto.domain.model.componentinternaluser.ComponentInternalUserModel
import com.example.hueverianieto.databinding.FragmentDeletedInternalUsersBinding
import com.example.hueverianieto.ui.views.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.ui.views.usersandclients.clients.DeletedClientsFragment
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeletedInternalUsersFragment : BaseFragment() {

    private lateinit var binding: FragmentDeletedInternalUsersBinding

    private var internalUserList: MutableList<ComponentInternalUserModel> = mutableListOf()

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun configureUI() {
        getInternalUsers()
    }

    override fun setObservers() {
        // TODO: Sin implementar
    }

    override fun setListeners() {
        // TODO: Sin implementar
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllInternalUsersActivity).configNav("Ver usuarios eliminados")
        this.binding = FragmentDeletedInternalUsersBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    private fun getInternalUsers() {
        Firebase.firestore
            .collection("user_info")
            .orderBy("id")
            .get()
            .addOnCompleteListener { task ->
                internalUserList = mutableListOf()
                if (task.isSuccessful) {
                    val documentList = task.result
                    if (!documentList.isEmpty) {
                        this.binding.deletedInternalUsersRecyclerView.visibility = View.VISIBLE
                        this.binding.containerWaringNoDeletedInternalUsers.visibility = View.GONE
                        for (document in documentList) {
                            Log.v("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Consulta correcta")
                            val doc = document.data as MutableMap<String, Any?>?
                            if (InternalUserUtils.checkErrorMap(doc) == null) {
                                val data = doc as MutableMap<String, Any?>
                                val userData = InternalUserUtils.mapToParcelable(data, document.id)
                                if (userData.deleted) {
                                    val componentInternalUserModel = ComponentInternalUserModel(
                                        userData.id,
                                        userData.name,
                                        userData.surname,
                                        userData.dni,
                                        "userData.role"
                                    ) {
                                        navigateToInternalUserDetails()
                                    }
                                    internalUserList.add(componentInternalUserModel)
                                }
                            } else {
                                // TODO
                                Log.e("CONSULTA", DeletedClientsFragment::class.java.simpleName + " - Error 1")
                            }
                        }
                        if (internalUserList.isEmpty()) {
                            this.binding.deletedInternalUsersRecyclerView.visibility = View.GONE
                            this.binding.containerWaringNoDeletedInternalUsers.visibility = View.VISIBLE
                            this.binding.containerWaringNoDeletedInternalUsers.setTitle("No hay usuarios internos eliminados")
                            this.binding.containerWaringNoDeletedInternalUsers.setText("No hay registro de clientes eliminados en la base de datos")
                        } else {
                            initRecyclerView()
                        }
                    } else {
                        this.binding.deletedInternalUsersRecyclerView.visibility = View.GONE
                        this.binding.containerWaringNoDeletedInternalUsers.visibility = View.VISIBLE
                        this.binding.containerWaringNoDeletedInternalUsers.setTitle("No hay usuarios internos eliminados")
                        this.binding.containerWaringNoDeletedInternalUsers.setText("No hay registro de clientes eliminados en la base de datos")
                    }
                } else {
                    // TODO
                    Log.e("CONSULTA", DeletedInternalUsersFragment::class.java.simpleName + " - Error 2")
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", DeletedInternalUsersFragment::class.java.simpleName + " - Error 3")
            }
    }

    private fun initRecyclerView() {
        this.binding.deletedInternalUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.deletedInternalUsersRecyclerView.adapter = ComponentInternalUserAdapter(internalUserList)
        this.binding.deletedInternalUsersRecyclerView.setHasFixedSize(false)
    }

    private fun navigateToInternalUserDetails() {
        // TODO: Navegación a pantalla de detalles
        Log.v("NAVEGACIÓN", DeletedInternalUsersFragment::class.java.simpleName + " - Aquí iría la navegación ")
    }

}