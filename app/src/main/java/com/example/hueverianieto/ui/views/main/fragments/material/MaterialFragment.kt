package com.example.hueverianieto.ui.views.main.fragments.material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMaterialBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MaterialFragment : BaseFragment() {

    private lateinit var binding: FragmentMaterialBinding
    private lateinit var currentUserData: InternalUserData

    private val materialViewModel: MaterialViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        currentUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentMaterialBinding.bind(
                inflater.inflate(
                    R.layout.fragment_material,
                    container,
                    false
                )
            )
        return binding.root
    }

    override fun configureUI() {
        setButtons()
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        this.binding.workersButton.setOnClickListener {
            this.materialViewModel.navigateToWorkersResources(
                this.requireContext(), currentUserData
            )
        }
        this.binding.hensButton.setOnClickListener {
            this.materialViewModel.navigateToHensResources(
                this.requireContext(), currentUserData
            )
        }
        this.binding.electricityWaterGasButton.setOnClickListener {
            this.materialViewModel.navigateToEWGResources(
                this.requireContext(), currentUserData
            )
        }
        this.binding.feedButton.setOnClickListener {
            this.materialViewModel.navigateToFeedResources(
                this.requireContext(), currentUserData
            )
        }
        this.binding.boxesCartonsButton.setOnClickListener {
            this.materialViewModel.navigateToBoxesAndCartonsResources(
                this.requireContext(), currentUserData
            )
        }
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    private fun setButtons() {
        with(this.binding) {
            this.seeAllButton.setText("Ver todo")
            this.seeAllButton.visibility = View.GONE
            this.workersButton.setText("Trabajadores y sueldos")
            this.machinesButton.setText("Maquinaria")
            this.machinesButton.visibility = View.GONE
            this.hensButton.setText("Gallinas")
            this.electricityWaterGasButton.setText("Luz, agua, gas")
            this.feedButton.setText("Pienso")
            this.petrolButton.setText("Gasolina")
            this.petrolButton.visibility = View.GONE
            this.boxesCartonsButton.setText("Cajas y cartones")
            this.trucksButton.setText("Camiones")
            this.trucksButton.visibility = View.GONE
            this.othersButton.setText("Otros")
            this.othersButton.visibility = View.GONE
        }
    }

}
