package com.example.hueverianieto.ui.views.main.fragments.ecconomy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentEcconomyBinding
import com.example.hueverianieto.databinding.FragmentHomeBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EcconomyFragment : BaseFragment() {

    private lateinit var binding: FragmentEcconomyBinding
    private lateinit var internalUserData: InternalUserData

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