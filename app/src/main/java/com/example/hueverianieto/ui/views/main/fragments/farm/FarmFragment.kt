package com.example.hueverianieto.ui.views.main.fragments.farm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFarmBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FarmFragment : BaseFragment() {

    private lateinit var binding: FragmentFarmBinding
    private lateinit var internalUserData: InternalUserData
    private val farmViewModel: FarmViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        (activity as BaseActivity).changeTopBarName("Granja")
        internalUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentFarmBinding.bind(inflater.inflate(R.layout.fragment_farm, container, false))
        return binding.root
    }

    override fun configureUI() {
        // Not necessary
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        this.binding.finalProductControlButton.setOnClickListener {
            this.farmViewModel.navigateToFinalProductControl(
                requireContext(),
                internalUserData
            )
        }
        this.binding.farmSituationMonitoringButton.setOnClickListener {
            this.farmViewModel.navigateToFinalMonitoringCompanySituation(
                requireContext(),
                internalUserData
            )
        }
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

}
