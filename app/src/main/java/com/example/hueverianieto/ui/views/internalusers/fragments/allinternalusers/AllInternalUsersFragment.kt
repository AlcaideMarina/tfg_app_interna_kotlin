package com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.ui.components.componentinternaluseradapter.ComponentInternalUserAdapter
import com.example.hueverianieto.domain.model.componentinternaluser.ComponentInternalUserModel
import com.example.hueverianieto.databinding.FragmentAllInternalUsersBinding
import com.example.hueverianieto.ui.views.main.fragments.usersandclients.UsersAndClientsFragment
import com.example.hueverianieto.ui.views.clients.fragments.allclients.AllClientsFragment
import com.example.hueverianieto.ui.views.internalusers.AllInternalUsersActivity
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllInternalUsersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllInternalUsersBinding

    private var internalUserList: MutableList<ComponentInternalUserModel> = mutableListOf()


    override fun configureUI() {
        this.binding.newInternalUserButton.isEnabled = true
        this.binding.newInternalUserButton.setText("Nuevo")
        this.binding.deletedInternalUsersButton.isEnabled = true
        this.binding.deletedInternalUsersButton.setText("Usuarios eliminados")
        getInternalUserListaData()
    }

    override fun setObservers() {
        // TODO: Sin implementar
    }

    override fun setListeners() {
        this.binding.deletedInternalUsersButton.setOnClickListener { navigateDeleteInternalUsers() }
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
        (activity as AllInternalUsersActivity).getToolbar().setNavigationOnClickListener { (activity as BaseActivity).goBackFragments() }
        this.binding = FragmentAllInternalUsersBinding
            .inflate(inflater, container, false)
        return this.binding.root
    }

    private fun getInternalUserListaData() {
        Firebase.firestore
            .collection("user_info")
            .orderBy("id")
            .get()
            .addOnCompleteListener { task ->
                internalUserList = mutableListOf()
                if (task.isSuccessful) {
                    val documentList = task.result
                    if (!documentList.isEmpty) {
                        this.binding.internalUsersRecyclerView.visibility = View.VISIBLE
                        this.binding.containerWaringNoInternalUsers.visibility = View.GONE
                        for (document in documentList) {
                            Log.v("CONSULTA", UsersAndClientsFragment::class.java.simpleName + " - Consulta correcta")
                            val doc = document.data as MutableMap<String, Any?>?
                            if (InternalUserUtils.checkErrorMap(doc) == null) {
                                val data = doc as MutableMap<String, Any?>
                                val userData = InternalUserUtils.mapToParcelable(data, document.id)
                                if (!userData.deleted) {
                                    val componentInternalUserModel = ComponentInternalUserModel(
                                        userData.id.toString(),
                                        userData.name,
                                        userData.surname,
                                        userData.dni,
                                        "userData.role"
                                    ) {
                                        // TODO
                                        Log.v("NAVEGACIÓN", AllInternalUsersFragment::class.java.simpleName)
                                    }
                                    internalUserList.add(componentInternalUserModel)
                                }
                            } else {
                                // TODO
                                Log.e("CONSULTA", AllInternalUsersFragment::class.java.simpleName + " hay un error en el objeto")
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
                    } else {
                        this.binding.internalUsersRecyclerView.visibility = View.GONE
                        this.binding.containerWaringNoInternalUsers.visibility = View.VISIBLE
                        this.binding.containerWaringNoInternalUsers.setTitle("No hay usuarios internos")
                        this.binding.containerWaringNoInternalUsers.setText("No hay registro de usuarios internos en la base de datos")
                    }
                } else {
                    // TODO
                    Log.e("CONSULTA", AllInternalUsersFragment::class.java.simpleName + " task.isSuccessful == false - " + task.exception?.message)
                }
            }.addOnFailureListener {
                // TODO
                Log.e("CONSULTA", AllInternalUsersFragment::class.java.simpleName + " fallo en la llamada")
            }
    }

    private fun initRecyclerView() {
        this.binding.internalUsersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.internalUsersRecyclerView.adapter = ComponentInternalUserAdapter(internalUserList)
        this.binding.internalUsersRecyclerView.setHasFixedSize(false)
    }

    private fun navigateDeleteInternalUsers() {
        this.view?.findNavController()?.navigate(R.id.action_allInternalUsersFragment_to_deletedInternalUsersFragment)
            ?: Log.e(
                AllClientsFragment::class.simpleName,
                "Error en la navegación en newClientButton"
            )
    }

}
