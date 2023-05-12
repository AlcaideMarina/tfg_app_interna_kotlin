package com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.databinding.FragmentWorkersDetailBinding
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity

class WorkerDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkersDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var internalUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        val args: WorkerDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        this.binding = FragmentWorkersDetailBinding
            .inflate(inflater, container, false)

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
