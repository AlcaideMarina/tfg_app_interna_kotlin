package com.example.hueverianieto.ui.views.main.fragments.home

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentHomeBinding
import com.example.hueverianieto.ui.views.main.MainActivity
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var currentUserData: InternalUserData

    private val homeViewModel: HomeViewModel by viewModels()

    private var serviceTodayOrders: Boolean = false
    private var serviceTodayDeliveries: Boolean = false
    private var serviceTodayMCS: Boolean = false

    override fun configureUI() {
        (activity as MainActivity).setToolbarTitle("Home")

        this.binding.welcomeText1.text = "Hola, " + currentUserData.name

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        this.homeViewModel.getTodayOrders()
        this.homeViewModel.getTodayDelivery()
        this.homeViewModel.getDailyMonitoringCompanySituation(Timestamp(calendar.time))
        lifecycleScope.launchWhenStarted {
            homeViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.homeViewModel.todayOrdersNumber.observe(this) { todayOrdersNumber ->
            setOrderNotification(todayOrdersNumber)
        }
        this.homeViewModel.todayDeliveriesNumber.observe(this) { todayDeliveriesNumber ->
            setDeliveriesNotification(todayDeliveriesNumber)
        }
        this.homeViewModel.mcsIsDone.observe(this) { isDone ->
            if (isDone) {
                binding.monitoringCompanySituationIcon.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_right)
                )
                this.binding.monitoringCompanySituationText.text =
                    "Hoy se ha hecho el seguimiento de la situación de la empresa."
            } else {
                binding.monitoringCompanySituationIcon.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_wrong)
                )
                this.binding.monitoringCompanySituationText.text =
                    "Hoy aún no se ha hecho el seguimiento de la situación de la empresa."
            }
        }
        this.homeViewModel.serviceGetTodayOrders.observe(this) { isDone ->
            serviceTodayOrders = isDone
        }
        this.homeViewModel.serviceGetTodayDeliveries.observe(this) { isDone ->
            serviceTodayDeliveries = isDone
        }
        this.homeViewModel.serviceGetTodayMCS.observe(this) { isDone ->
            serviceTodayMCS = isDone
        }
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        state as HomeViewState
        if (serviceTodayOrders == serviceTodayDeliveries && serviceTodayOrders == serviceTodayMCS) {
            this.binding.loadingComponent.isVisible = state.isLoading
            this.binding.baseComponent.isVisible = !state.isLoading
        }
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
        this.binding.ordersNotificationText.text =
            "Hoy hay " + todayOrdersNumber.toString() + " pedidos."
    }

    private fun setDeliveriesNotification(todayDeliveriesNumber: Int) {
        this.binding.deliverNotificationText.text =
            "Hay " + todayDeliveriesNumber.toString() + " repartos planificados para hoy."
    }

}
