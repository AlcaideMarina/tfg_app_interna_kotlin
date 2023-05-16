package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMonthlyMonitoringCompanySituationBinding
import com.example.hueverianieto.ui.views.monitoringcompanysituation.MonitoringCompanySituationActivity

class MonthlyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentMonthlyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as MonitoringCompanySituationActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        currentUserData = (activity as MonitoringCompanySituationActivity).currentUserData

        this.binding = FragmentMonthlyMonitoringCompanySituationBinding.inflate(
            inflater, container, false
        )
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