package com.example.hueverianieto.ui.views.main.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentHomeBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var currentUserData: InternalUserData

    private val homeViewModel: HomeViewModel by viewModels()

    override fun configureUI() {
        this.homeViewModel.getTodayOrders()
        this.homeViewModel.getTodayDelivery()
    }

    override fun setObservers() {
        this.homeViewModel.todayOrdersNumber.observe(this) { todayOrdersNumber ->
            setOrderNotification(todayOrdersNumber)
        }
        this.homeViewModel.todayDeliveriesNumber.observe(this) { todayDeliveriesNumber ->
            setDeliveriesNotification(todayDeliveriesNumber)
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(false)
        this.currentUserData = (activity as MainActivity).currentUserData
        this.binding =
            FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    private fun setOrderNotification(todayOrdersNumber: Int) {
        this.binding.ordersNotificationText.text = "Hoy hay " + todayOrdersNumber.toString() + " pedidos."
    }

    private fun setDeliveriesNotification(todayDeliveriesNumber: Int) {
        this.binding.deliverNotificationText.text = "Hay " + todayDeliveriesNumber.toString() + " repartos planificados para hoy."
    }

}
