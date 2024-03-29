package com.example.hueverianieto.ui.views.workersresources.fragment.pendingworkersresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllWorkersResourcesBinding
import com.example.hueverianieto.domain.model.componentworkers.ComponentWorkersModel
import com.example.hueverianieto.ui.components.componentworker.HNComponentWorkerAdapter
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendingWorkersResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllWorkersResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var pendingWorkersList: MutableList<ComponentWorkersModel>

    private val pendingWorkersResourcesViewModel: PendingWorkersResourcesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        currentUserData = (activity as WorkersResourcesActivity).currentUserData

        val args: PendingWorkersResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentAllWorkersResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.pendingWorkersResourcesViewModel.getAllWorkers()
        this.binding.pendingWorkersButton.visibility = View.GONE
    }

    override fun setObservers() {
        this.pendingWorkersResourcesViewModel.workerList.observe(this) { internalUserDataList ->
            if (internalUserDataList == null) {
                this.binding.workersRecyclerView.visibility = View.GONE
                this.binding.containerWaringNoWorkers.visibility = View.VISIBLE
                this.binding.containerWaringNoWorkers.setTitle("Error")
                this.binding.containerWaringNoWorkers.setText("Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.")
            } else {
                pendingWorkersList = mutableListOf()
                for (internalUserData in internalUserDataList) {
                    if (internalUserData != null) {
                        val componentWorkersModel = ComponentWorkersModel(
                            internalUserData.id.toString(),
                            internalUserData.name,
                            internalUserData.surname,
                            internalUserData.salary
                        ) {
                            this.pendingWorkersResourcesViewModel.navigateToWorkerDetail(
                                this.view,
                                bundleOf(
                                    "currentUserData" to currentUserData,
                                    "internalUserData" to internalUserData
                                )
                            )
                        }
                        if (internalUserData.salary == null) {
                            pendingWorkersList.add(componentWorkersModel)
                        }
                    }
                }
                if (pendingWorkersList.isEmpty()) {
                    this.binding.workersRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoWorkers.visibility = View.VISIBLE
                    this.binding.containerWaringNoWorkers.setTitle("No hay usuarios con sueldo pendiente")
                    this.binding.containerWaringNoWorkers.setText("No hay registro de usuarios internos activos en la base de datos que estén pendientes de asignar el salario.")
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
        // Not necessary
    }

    private fun initRecyclerView() {
        this.binding.workersRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.workersRecyclerView.adapter = HNComponentWorkerAdapter(pendingWorkersList)
        this.binding.workersRecyclerView.setHasFixedSize(false)
    }

}
