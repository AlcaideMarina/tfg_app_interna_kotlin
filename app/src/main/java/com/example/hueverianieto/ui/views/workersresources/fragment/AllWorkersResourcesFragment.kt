package com.example.hueverianieto.ui.views.workersresources.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllWorkersResourcesBinding
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllWorkersResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllWorkersResourcesBinding
    private lateinit var currentUserData: InternalUserData

    private var internalUserDataList: List<InternalUserData> = mutableListOf()

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
        this.binding.newWorkersButton.setText("Nuevos trabajadores")
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
