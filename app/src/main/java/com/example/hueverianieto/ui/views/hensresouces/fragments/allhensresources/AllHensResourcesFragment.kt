package com.example.hueverianieto.ui.views.hensresouces.fragments.allhensresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllHensResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentAllHensResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val allHensResourcesViewModel: AllHensResourcesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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