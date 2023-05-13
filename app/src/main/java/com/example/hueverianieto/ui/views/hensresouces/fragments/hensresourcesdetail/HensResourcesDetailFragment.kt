package com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ComponentTicketBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HensResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentHensResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var componentTicketModel: ComponentTicketModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        this.binding = FragmentHensResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root

    }

    override fun configureUI() {
        TODO("Not yet implemented")
    }

    override fun setObservers() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        TODO("Not yet implemented")
    }

}
