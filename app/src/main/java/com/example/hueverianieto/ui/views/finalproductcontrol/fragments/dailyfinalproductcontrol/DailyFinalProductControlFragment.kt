package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentDailyFinalProductControlBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.MonthlyFPCContainerModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DailyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyFinalProductControlBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var monthlyFPCContainerModel: MonthlyFPCContainerModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as BaseActivity).configNav(true)

        this.binding = FragmentDailyFinalProductControlBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        TODO("Not yet implemented")
    }

    override fun setObservers() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        TODO("Not yet implemented")
    }
}