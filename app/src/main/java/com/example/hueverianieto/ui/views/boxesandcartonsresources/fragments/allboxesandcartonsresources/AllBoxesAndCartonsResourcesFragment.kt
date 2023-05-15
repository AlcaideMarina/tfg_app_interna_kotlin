package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.allboxesandcartonsresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllBoxesAndCartonsResourcesBinding
import com.example.hueverianieto.databinding.FragmentAllFeedResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.ui.views.feedresources.FeedResourcesActivity
import kotlinx.coroutines.flow.collect

class AllBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllBoxesAndCartonsResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allBoxesAndCartonsResourcesViewModel: AllBoxesAndCartonsResourcesViewModel by viewModels()

    private var boxesAndCartonsDataList: MutableList<ComponentTicketModel> = mutableListOf()

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
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        with(state as AllBoxesAndCartonsResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

}
