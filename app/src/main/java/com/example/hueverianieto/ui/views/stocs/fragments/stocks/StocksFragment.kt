package com.example.hueverianieto.ui.views.stocs.fragments.stocks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentStocksBinding
import com.example.hueverianieto.ui.views.stocs.StocksActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StocksFragment : BaseFragment() {

    private lateinit var binding: FragmentStocksBinding
    private lateinit var currentUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as BaseActivity).configNav(true)
        (activity as StocksActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }


        this.binding = FragmentStocksBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.binding.hensStockButton.setText("Gallinas")
        this.binding.boxesStockButton.setText("Cajas")
        this.binding.cartonsStockButton.setText("Cartones")
    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        // TODO
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }
}