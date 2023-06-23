package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.allboxesandcartonsresources

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
import com.example.hueverianieto.databinding.FragmentAllBoxesAndCartonsResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.components.componentticket.HNComponentTicketAdapter
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.utils.MaterialUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllBoxesAndCartonsResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allBoxesAndCartonsResourcesViewModel: AllBoxesAndCartonsResourcesViewModel by viewModels()

    private var bcDataList: MutableList<ComponentTicketModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as BoxesAndCartonsActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as BoxesAndCartonsActivity).currentUserData

        this.binding = FragmentAllBoxesAndCartonsResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.allBoxesAndCartonsResourcesViewModel.getCB()
        lifecycleScope.launchWhenStarted {
            allBoxesAndCartonsResourcesViewModel.getCB()
            allBoxesAndCartonsResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.allBoxesAndCartonsResourcesViewModel.cbList.observe(this) { cbResourcesDataList ->
            if (cbResourcesDataList == null) {
                this.binding.boxesAndCartonsRecyclerView.visibility = View.GONE
                this.binding.containerWaringNoOrders.visibility = View.VISIBLE
                this.binding.containerWaringNoOrders.setTitle("Error")
                this.binding.containerWaringNoOrders.setText("Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.")
            } else {
                bcDataList = mutableListOf()
                for (bcResourcesData in cbResourcesDataList) {
                    if (bcResourcesData != null) {
                        val componentTicketModel = ComponentTicketModel(
                            bcResourcesData.expenseDatetime,
                            MaterialUtils.getBCOrderSummary(
                                MaterialUtils.bcOrderToDBBoxesAndCartonsOrderModel(
                                    bcResourcesData
                                )
                            ),
                            "",
                            bcResourcesData.totalPrice
                        ) {
                            this.allBoxesAndCartonsResourcesViewModel.navigationToBCResourcesDetail(
                                this.view,
                                bundleOf(
                                    "currentUserData" to currentUserData,
                                    "bcResourcesData" to bcResourcesData
                                )
                            )
                        }
                        bcDataList.add(componentTicketModel)
                    }
                }
                if (bcDataList.isEmpty()) {
                    this.binding.boxesAndCartonsRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoOrders.visibility = View.VISIBLE
                    this.binding.containerWaringNoOrders.setTitle("No hay recursos")
                    this.binding.containerWaringNoOrders.setText("No hay registro de recursos de cajas y cartones sin eliminar en la base de datos.")
                } else {
                    initRecyclerView()
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.addButton.setOnClickListener {
            this.allBoxesAndCartonsResourcesViewModel.navigationToNewBCResources(
                this.view,
                bundleOf(
                    "currentUserData" to this.currentUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as AllBoxesAndCartonsResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun initRecyclerView() {
        this.binding.boxesAndCartonsRecyclerView.visibility = View.VISIBLE
        this.binding.containerWaringNoOrders.visibility = View.GONE
        this.binding.boxesAndCartonsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.boxesAndCartonsRecyclerView.adapter = HNComponentTicketAdapter(bcDataList)
        this.binding.boxesAndCartonsRecyclerView.setHasFixedSize(false)
    }

}
