package com.example.hueverianieto.ui.views.finalproductcontrol.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMonthlyFinalProductControlBinding
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import com.example.hueverianieto.ui.views.finalproductcontrol.FinalProductControlActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthlyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentMonthlyFinalProductControlBinding
    private lateinit var currentUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as FinalProductControlActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        this.currentUserData = (activity as FinalProductControlActivity).currentUserData

        this.binding = FragmentMonthlyFinalProductControlBinding.inflate(
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
