package com.example.hueverianieto.ui.views.main.fragments.ecconomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentEcconomyBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EconomyFragment : BaseFragment() {

    private lateinit var binding: FragmentEcconomyBinding
    private lateinit var internalUserData: InternalUserData
    private val economyViewModel: EconomyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        internalUserData = (activity as MainActivity).currentUserData
        binding =
            FragmentEcconomyBinding.bind(inflater.inflate(R.layout.fragment_ecconomy, container, false))
        return binding.root
    }

    override fun configureUI() {
      setButtons()
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        this.binding.clientsBillingButton.setOnClickListener {
            this.economyViewModel.navigateToClientsBilling(
                requireContext(), this.internalUserData)
        }
        this.binding.companyAccountsButton.setOnClickListener {
            this.economyViewModel.navigateToSellingPrice(
                requireContext(), this.internalUserData
            )
        }
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    private fun setButtons() {
        this.binding.clientsBillingButton.setText("Facturación de clientes")
        this.binding.companyAccountsButton.setText("Contabilidad")
    }
}