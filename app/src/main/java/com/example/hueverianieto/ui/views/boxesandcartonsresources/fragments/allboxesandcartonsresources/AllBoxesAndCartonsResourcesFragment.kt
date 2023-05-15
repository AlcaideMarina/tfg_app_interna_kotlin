package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.allboxesandcartonsresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllBoxesAndCartonsResourcesBinding
import com.example.hueverianieto.databinding.FragmentAllFeedResourcesBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.ui.views.feedresources.FeedResourcesActivity

class AllBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllBoxesAndCartonsResourcesBinding
    private lateinit var currentUserData: InternalUserData

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
