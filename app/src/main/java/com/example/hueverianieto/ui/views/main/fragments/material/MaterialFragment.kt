package com.example.hueverianieto.ui.views.main.fragments.material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMaterialBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MaterialFragment : BaseFragment() {

    private lateinit var binding : FragmentMaterialBinding
    private lateinit var internalUserData : InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        internalUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentMaterialBinding.bind(inflater.inflate(R.layout.fragment_material, container, false))
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
        with(this.binding) {
            this.seeAllButton.setText("Ver todo")
            this.workersButton.setText("Trabajadores y sueldos")
            this.machinesButton.setText("Maquinaria")
            this.hensButton.setText("Gallinas")
            this.electricityWaterGasButton.setText("Luz, agua, gas")
            this.feedButton.setText("Pienso")
            this.petrolButton.setText("Gasolina")
            this.boxesCartonsButton.setText("Cajas y cartones")
            this.trucksButton.setText("Camiones")
            this.othersButton.setText("Otros")
        }
    }

}