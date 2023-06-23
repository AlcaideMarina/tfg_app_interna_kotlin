package com.example.hueverianieto.ui.views.main.fragments.material

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
        (activity as BaseActivity).changeTopBarName("Material")
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
        // Not necessary
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

}
