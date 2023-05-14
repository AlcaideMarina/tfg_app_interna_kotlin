package com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces

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
import com.example.hueverianieto.databinding.FragmentAllFeedResourcesBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.components.componentticket.HNComponentTicketAdapter
import com.example.hueverianieto.ui.views.feedresources.FeedResourcesActivity
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AllFeedResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllFeedResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allFeedResourcesViewModel: AllFeedResourcesViewModel by viewModels()

    private var feedDataList: MutableList<ComponentTicketModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as FeedResourcesActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as FeedResourcesActivity).currentUserData

        this.binding = FragmentAllFeedResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.allFeedResourcesViewModel.getFeed()
        lifecycleScope.launchWhenStarted {
            allFeedResourcesViewModel.getFeed()
            allFeedResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.allFeedResourcesViewModel.feedList.observe(this) { feedResourcesDataList ->
            if (feedResourcesDataList == null) {
                // TODO: ERROR
            } else {
                feedDataList = mutableListOf()
                for (hensResourcesData in feedResourcesDataList)  {
                    if (hensResourcesData != null) {
                        val componentTicketModel = ComponentTicketModel(
                            hensResourcesData.expenseDatetime,
                            hensResourcesData.kilos.toString(),
                            "kg",
                            hensResourcesData.totalPrice
                        ) {
                            // TODO: Navegación
                        }
                        feedDataList.add(componentTicketModel)
                    }
                }
                if (feedDataList.isEmpty()) {
                    this.binding.feedRecyclerView.visibility = View.GONE
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
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        with(state as AllFeedResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun initRecyclerView() {
        this.binding.feedRecyclerView.visibility = View.VISIBLE
        this.binding.containerWaringNoOrders.visibility = View.GONE
        this.binding.feedRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.feedRecyclerView.adapter = HNComponentTicketAdapter(feedDataList)
        this.binding.feedRecyclerView.setHasFixedSize(false)
    }
}
