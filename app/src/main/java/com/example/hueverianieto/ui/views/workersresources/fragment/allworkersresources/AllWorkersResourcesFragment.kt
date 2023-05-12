package com.example.hueverianieto.ui.views.workersresources.fragment.allworkersresources

import android.os.Bundle
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
import com.example.hueverianieto.databinding.FragmentAllWorkersResourcesBinding
import com.example.hueverianieto.domain.model.componentinternaluser.ComponentInternalUserModel
import com.example.hueverianieto.domain.model.componentworkers.ComponentWorkersModel
import com.example.hueverianieto.ui.components.componentinternaluseradapter.ComponentInternalUserAdapter
import com.example.hueverianieto.ui.components.componentworker.HNComponentWorkerAdapter
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AllWorkersResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllWorkersResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allWorkersResourcesViewModel: AllWorkersResourcesViewModel by viewModels()

    private var workerList: MutableList<ComponentWorkersModel> = mutableListOf()
    private var pendingWorkersList: MutableList<ComponentWorkersModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as WorkersResourcesActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as WorkersResourcesActivity).currentUserData

        this.binding = FragmentAllWorkersResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.allWorkersResourcesViewModel.getAllWorkers()
        this.binding.pendingWorkersButton.setText("Sueldos pendientes")
        lifecycleScope.launchWhenStarted {
            allWorkersResourcesViewModel.getAllWorkers()
            allWorkersResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.allWorkersResourcesViewModel.workerList.observe(this) { internalUserDataList ->
            if (internalUserDataList == null) {
                // TODO: ERROR
            } else {
                workerList = mutableListOf()
                for (internalUserData in internalUserDataList) {
                    if (internalUserData != null) {
                        val componentWorkersModel = ComponentWorkersModel(
                            internalUserData.id.toString(),
                            internalUserData.name,
                            internalUserData.surname,
                            internalUserData.salary
                        ) {
                            // TODO navegación
                        }
                        if (internalUserData.salary != null) {
                            workerList.add(componentWorkersModel)
                        } else {
                            pendingWorkersList.add(componentWorkersModel)
                        }
                    }
                }
                if (workerList.isEmpty()) {
                    this.binding.workersRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoWorkers.visibility = View.VISIBLE
                    this.binding.containerWaringNoWorkers.setTitle("No hay trabajadores con sueldo asignado")
                    this.binding.containerWaringNoWorkers.setText("No hay registro de usuarios trabajadores que ya cuenten con un sueldo asignado en la base de datos")
                } else {
                    initRecyclerView()
                }
                if (pendingWorkersList.isEmpty()) {
                    this.binding.pendingWorkersButton.visibility = View.GONE
                } else {
                    this.binding.pendingWorkersButton.setOnClickListener {
                        this.allWorkersResourcesViewModel.navigateToPendingWorkers(
                            this.view,
                            bundleOf(
                                "currentUserData" to currentUserData
                            )
                        )
                    }
                }
            }
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        with(state as AllWorkersResourcesViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun initRecyclerView() {
        this.binding.workersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.workersRecyclerView.adapter = HNComponentWorkerAdapter(workerList)
        this.binding.workersRecyclerView.setHasFixedSize(false)
    }

}
