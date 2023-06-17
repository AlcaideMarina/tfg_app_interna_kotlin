package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.weeklymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentWeeklyMonitoringCompanySituationBinding
import com.example.hueverianieto.domain.model.componentdatedivisionmodel.ComponentDailyDivisionDateFilter
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn

@AndroidEntryPoint
class WeeklyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentWeeklyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var initTimestamp: Timestamp
    private lateinit var endTimestamp: Timestamp
    private val weeklyMonitoringCompanySituationViewModel: WeeklyMonitoringCompanySituationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: WeeklyMonitoringCompanySituationFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.initTimestamp = args.dateFilter.initTimestamp
        this.endTimestamp = args.dateFilter.endTimestamp

        this.binding = FragmentWeeklyMonitoringCompanySituationBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        this.weeklyMonitoringCompanySituationViewModel.getWeeklyMonitoringCompanySituation(initTimestamp, endTimestamp)
        setDataTexts()
        lifecycleScope.launchWhenStarted {
            weeklyMonitoringCompanySituationViewModel.getWeeklyMonitoringCompanySituation(initTimestamp, endTimestamp)
            weeklyMonitoringCompanySituationViewModel.viewState.collect() { stateView ->
                updateUI(stateView)
            }
        }
    }

    override fun setObservers() {
        this.weeklyMonitoringCompanySituationViewModel.weeklyMonitoringCompanySituationData.observe(this) { data ->
            if (data != null) {
                this.binding.weeklyXlLaying.text = "Puesta semanal - XL:  " + data.xlEggs.toString()
                this.binding.weeklyLLaying.text = "Puesta semanal - L:  " + data.lEggs.toString()
                this.binding.weeklyMLaying.text = "Puesta semanal - M:  " + data.mEggs.toString()
                this.binding.weeklySLaying.text = "Puesta semanal - S:  " + data.sEggs.toString()
                this.binding.weeklyTotalLaying.text = "Puesta semanal (total):  " + data.weeklyLaying.toString()
                this.binding.hensLossesWeeklyLaying.text = "Bajas de gallinas esta semana:  " + data.hensLosses.toString()
            } else {
                this.binding.weeklyXlLaying.text = "Puesta semanal - XL:  0"
                this.binding.weeklyLLaying.text = "Puesta semanal - L:  0"
                this.binding.weeklyMLaying.text = "Puesta semanal - M:  0"
                this.binding.weeklySLaying.text = "Puesta semanal - S:  0"
                this.binding.weeklyTotalLaying.text = "Puesta semanal (total):  0"
                this.binding.hensLossesWeeklyLaying.text = "Bajas de gallinas esta semana:  0"
            }
        }
    }

    override fun setListeners() {
        this.binding.mondayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(0)
        }
        this.binding.tuesdayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(1)
        }
        this.binding.wednesdayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(2)
        }
        this.binding.thursdayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(3)
        }
        this.binding.fridayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(4)
        }
        this.binding.saturdayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(5)
        }
        this.binding.sundayBaseComponentContainer.setOnClickListener {
            navigateToDailyDetail(6)
        }
    }

    override fun updateUI(state: BaseState) {
        state as WeeklyMonitoringCompanySituationViewState
        this.binding.loadingComponent.isVisible = state.isLoading
    }

    private fun setDataTexts() {
        this.binding.mondayText.text = "Lunes - " +
                Utils.parseTimestampToString(initTimestamp, "dd, MMMM, yyyy")
        this.binding.tuesdayText.text = "Martes - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 1)), "dd, MMMM, yyyy")
        this.binding.wednesdayText.text = "Miércoles - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 2)), "dd, MMMM, yyyy")
        this.binding.thursdayText.text = "Jueves - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 3)), "dd, MMMM, yyyy")
        this.binding.fridayText.text = "Viernes - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 4)), "dd, MMMM, yyyy")
        this.binding.saturdayDateText.text = "Sábado - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 5)), "dd, MMMM, yyyy")
        this.binding.sundayText.text = "Domingo - " +
                Utils.parseTimestampToString(Timestamp(Utils.addToDate(initTimestamp.toDate(), 6)), "dd, MMMM, yyyy")
    }

    private fun navigateToDailyDetail(daysToAdd: Int) {
        this.weeklyMonitoringCompanySituationViewModel
            .navigateToDailyMonitoringCompanySituation(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "date" to ComponentDailyDivisionDateFilter(
                        Timestamp(Utils.addToDate(initTimestamp.toDate(), daysToAdd))
                    )
                )
            )
    }


}
