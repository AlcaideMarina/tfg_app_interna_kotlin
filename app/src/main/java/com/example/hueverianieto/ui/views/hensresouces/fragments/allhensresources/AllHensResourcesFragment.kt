package com.example.hueverianieto.ui.views.hensresouces.fragments.allhensresources

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
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.domain.model.componentclientmodel.ComponentClientBillingModel
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.components.componentclientbilling.ComponentClientBillingAdapter
import com.example.hueverianieto.ui.components.componentticket.HNComponentTicketAdapter
import com.example.hueverianieto.ui.views.clientsbilling.fragments.clientsbilling.ClientsBillingViewState
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class AllHensResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllHensResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allHensResourcesViewModel: AllHensResourcesViewModel by viewModels()

    private var hensDataList: MutableList<ComponentTicketModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as HensResourcesActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as HensResourcesActivity).currentUserData

        this.binding = FragmentAllHensResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.allHensResourcesViewModel.getHens()
        lifecycleScope.launchWhenStarted {
            allHensResourcesViewModel.getHens()
            allHensResourcesViewModel.viewState.collect { state ->
                updateUI(state)
            }
        }
    }

    override fun setObservers() {
        this.allHensResourcesViewModel.hensList.observe(this) { hensResourcesDataList ->
            if (hensResourcesDataList == null) {
                // TODO: ERROR
            } else {
                hensDataList = mutableListOf()
                for (hensResourcesData in hensResourcesDataList)  {
                    if (hensResourcesData != null) {
                        val componentTicketModel = ComponentTicketModel(
                            hensResourcesData.expenseDatetime,
                            hensResourcesData.hensNumber.toString(),
                            "gallinas",
                            hensResourcesData.totalPrice
                        ) {
                            this.allHensResourcesViewModel.navigationToHensResourcesDetail(
                                this.view,
                                bundleOf(
                                    "currentUserData" to currentUserData,
                                    "hensResourcesData" to hensResourcesData
                                )
                            )
                        }
                        hensDataList.add(componentTicketModel)
                    }
                }
                if (hensDataList.isEmpty()) {
                    this.binding.hensRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoOrders.visibility = View.VISIBLE
                    this.binding.containerWaringNoOrders.setTitle("No hay clientes")
                    this.binding.containerWaringNoOrders.setText("No hay registro de clientes activos en la base de datos")
                } else {
                    initRecyclerView()
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.addButton.setOnClickListener {
            this.allHensResourcesViewModel.navigateToNewHensResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as AllHensResourcesViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun initRecyclerView() {
        this.binding.hensRecyclerView.visibility = View.VISIBLE
        this.binding.containerWaringNoOrders.visibility = View.GONE
        this.binding.hensRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.hensRecyclerView.adapter = HNComponentTicketAdapter(hensDataList)
        this.binding.hensRecyclerView.setHasFixedSize(false)
    }

}
