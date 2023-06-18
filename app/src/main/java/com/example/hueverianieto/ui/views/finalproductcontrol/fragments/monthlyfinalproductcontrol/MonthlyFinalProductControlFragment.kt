package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.monthlyfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMonthlyFinalProductControlBinding
import com.example.hueverianieto.domain.model.finalproductcontrol.FPCContainerItemModel
import com.example.hueverianieto.ui.components.componentbillingpermonth.finalproductcontrol.ComponentMonthlyFPCAdapter
import com.example.hueverianieto.ui.views.finalproductcontrol.FinalProductControlActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MonthlyFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentMonthlyFinalProductControlBinding
    private lateinit var currentUserData: InternalUserData
    private val monthlyFinalProductControlViewModel: MonthlyFinalProductControlViewModel by viewModels()

    private var lastLot = (0).toLong()

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
        this.monthlyFinalProductControlViewModel.getMonthlyFPCData()
        this.monthlyFinalProductControlViewModel.getNextLot()
        lifecycleScope.launchWhenStarted {
            monthlyFinalProductControlViewModel.getMonthlyFPCData()
            monthlyFinalProductControlViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.monthlyFinalProductControlViewModel.monthlyFPCContainerModelList.observe(this) { monthlyFPCContainerModelList ->
            if (monthlyFPCContainerModelList == null) {
                // TODO
            } else {
                val list = mutableListOf<FPCContainerItemModel>()
                for (item in monthlyFPCContainerModelList) {
                    if (item != null) {
                        val fpcContainerItemModel = FPCContainerItemModel(
                            item
                        ) {
                            this.monthlyFinalProductControlViewModel.navigateToDalyFPC(
                                this.view,
                                bundleOf(
                                    "currentUserData" to currentUserData,
                                    "monthlyFPCContainerModel" to item
                                )
                            )
                        }
                        list.add(fpcContainerItemModel)
                    }
                }
                if (list.isEmpty()) {
                    this.binding.monthlyFpcRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoMonthlyFpc.visibility = View.VISIBLE
                    this.binding.containerWaringNoMonthlyFpc.setTitle("No hay registros")
                    this.binding.containerWaringNoMonthlyFpc.setText("No hay registros en la base de datos.")
                } else {
                    this.binding.monthlyFpcRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.monthlyFpcRecyclerView.adapter = ComponentMonthlyFPCAdapter(list)
                    this.binding.monthlyFpcRecyclerView.visibility = View.VISIBLE
                    this.binding.containerWaringNoMonthlyFpc.visibility = View.GONE
                }
            }
        }
        this.monthlyFinalProductControlViewModel.lot.observe(this) { lot ->
            lastLot = (lot).toLong()
        }
    }

    override fun setListeners() {
        this.binding.addButton.setOnClickListener {
            this.monthlyFinalProductControlViewModel.navigateToNewFPC(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "lastLot" to lastLot
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as MonthlyFinalProductControlViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

}
