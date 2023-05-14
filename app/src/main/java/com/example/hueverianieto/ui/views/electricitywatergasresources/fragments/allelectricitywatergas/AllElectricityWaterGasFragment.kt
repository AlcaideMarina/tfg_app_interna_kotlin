package com.example.hueverianieto.ui.views.electricitywatergasresources.fragments.allelectricitywatergas

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
import com.example.hueverianieto.databinding.FragmentAllElectricityWaterGasResourcesBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.components.componentticket.HNComponentTicketAdapter
import com.example.hueverianieto.ui.views.electricitywatergasresources.ElectricityWaterGasResourcesActivity
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllElectricityWaterGasFragment : BaseFragment() {

    private lateinit var binding: FragmentAllElectricityWaterGasResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allEWGViewModel: AllElectricityWaterGasViewModel by viewModels()

    private var ewgDataList: MutableList<ComponentTicketModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as ElectricityWaterGasResourcesActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as ElectricityWaterGasResourcesActivity).currentUserData

        this.binding = FragmentAllElectricityWaterGasResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.allEWGViewModel.getEWG()
        lifecycleScope.launchWhenStarted {
            allEWGViewModel.getEWG()
            allEWGViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.allEWGViewModel.ewgList.observe(this) { ewgResourcesDataList ->
            if (ewgResourcesDataList == null) {
                // TODO: Error
            } else {
                ewgDataList = mutableListOf()
                for (ewgResourcesData in ewgResourcesDataList)  {
                    if (ewgResourcesData != null) {
                        val componentTicketModel = ComponentTicketModel(
                            ewgResourcesData.expenseDatetime,
                            ewgResourcesData.type.toString(),       // TOOD: ESTO HAY QUE PASARLO POR UN MAP
                            "",
                            ewgResourcesData.totalPrice
                        ) {
                            // TODO: Navegación
                        }
                        ewgDataList.add(componentTicketModel)
                    }
                }
                if (ewgDataList.isEmpty()) {
                    this.binding.electricityWaterGasRecyclerView.visibility = View.GONE
                    this.binding.containerWarningNoTickets.visibility = View.VISIBLE
                    this.binding.containerWarningNoTickets.setTitle("No hay clientes")
                    this.binding.containerWarningNoTickets.setText("No hay registro de clientes activos en la base de datos")
                } else {
                    initRecyclerView()
                }
            }
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        with(state as AllElectricityWaterGasViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun initRecyclerView() {
        this.binding.electricityWaterGasRecyclerView.visibility = View.VISIBLE
        this.binding.containerWarningNoTickets.visibility = View.GONE
        this.binding.electricityWaterGasRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.electricityWaterGasRecyclerView.adapter = HNComponentTicketAdapter(ewgDataList)
        this.binding.electricityWaterGasRecyclerView.setHasFixedSize(false)
    }

}
