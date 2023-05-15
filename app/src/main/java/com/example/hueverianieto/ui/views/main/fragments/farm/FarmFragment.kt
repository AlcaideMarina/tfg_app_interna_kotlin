package com.example.hueverianieto.ui.views.main.fragments.farm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.R
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        internalUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentFarmBinding.bind(inflater.inflate(R.layout.fragment_farm, container, false))
        return binding.root
    }

    override fun configureUI() {
        setButtons()
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

    private fun setButtons() {
        this.binding.finalProductControlButton.setText("Control prod. final")
        this.binding.farmSituationMonitoringButton.setText("Seg. situación granja")
        this.binding.stocksButton.setText("Existencias")
    }

}
