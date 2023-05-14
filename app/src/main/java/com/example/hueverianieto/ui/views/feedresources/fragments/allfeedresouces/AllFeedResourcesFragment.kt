package com.example.hueverianieto.ui.views.feedresources.fragments.allfeedresouces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllFeedResourcesBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.views.feedresources.FeedResourcesActivity
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFeedResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllFeedResourcesBinding
    private lateinit var currentUserData: InternalUserData

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
        currentUserData = (activity as HensResourcesActivity).currentUserData

        this.binding = FragmentAllFeedResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        //TODO("Not yet implemented")
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }
}
