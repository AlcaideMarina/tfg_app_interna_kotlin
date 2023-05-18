package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMonthlyMonitoringCompanySituationBinding
import com.example.hueverianieto.domain.model.componentweekdivisionmodel.ComponentWeekDivisionDateFilter
import com.example.hueverianieto.domain.model.componentweekdivisionmodel.ComponentWeekDivisionModel
import com.example.hueverianieto.ui.components.HNMonthYearPickerDialog
import com.example.hueverianieto.ui.components.componentweekdivision.ComponentWeekDivisionAdapter
import com.example.hueverianieto.ui.views.monitoringcompanysituation.MonitoringCompanySituationActivity
import com.example.hueverianieto.utils.Utils
import com.example.hueverianieto.utils.Utils.capitalizeFirstChar
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MonthlyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentMonthlyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private val monthlyMonitoringCompanySituationViewModel: MonthlyMonitoringCompanySituationViewModel by viewModels()

    private lateinit var alertDialog: HNMonthYearPickerDialog

    private var initFilterDatetime = Date()
    private var endFilterDatetime = Date()
    
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

        this.alertDialog = HNMonthYearPickerDialog(requireContext())
        this.binding = FragmentMonthlyMonitoringCompanySituationBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }


    override fun configureUI() {
        getDateFilterText()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.baseDateFilter.setOnClickListener {
            alertDialog.show(
                requireContext(),
            ) {
                val month = alertDialog.getMonthPicker().value
                val year = alertDialog.getYearPicker().value

                var m = month.toString()
                while (m.length < 2) m = "0" + m
                var y = year.toString()
                while (y.length < 4) y = "0" + y

                initFilterDatetime = Utils.parseStringToTimestamp("01/" + m + "/"+ y, "dd/MM/yyyy").toDate()
                getDateFilterText()

                alertDialog.cancel()
            }
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun getDateFilterText() {
        val calendar = Calendar.getInstance()
        calendar.time = initFilterDatetime
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        var m = (month + 1).toString()
        while (m.length < 2) m = "0" + m
        var y = year.toString()
        while (y.length < 4) y = "0" + y
        initFilterDatetime = Utils.parseStringToTimestamp("01/" + m + "/"+ y, "dd/MM/yyyy").toDate()
        endFilterDatetime = Utils.addToDate(initFilterDatetime, 0, 1)

        this.binding.textDateFilter.text = Utils.parseDateToString(initFilterDatetime, "MMMM, yyyy").capitalizeFirstChar()

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val initCalendar = Calendar.getInstance()
        initCalendar.time = initFilterDatetime
        val initDayOfWeek = initCalendar.get(Calendar.DAY_OF_WEEK)
        var firstDate = Utils.addToDate(initFilterDatetime, 2 - initDayOfWeek)

        val endCalendar = Calendar.getInstance()
        endCalendar.time = endFilterDatetime
        val endDayOfWeek = endCalendar.get(Calendar.DAY_OF_WEEK)
        val endDate = Utils.addToDate(endFilterDatetime, 8 - endDayOfWeek)

        val list = mutableListOf<ComponentWeekDivisionModel>()
        while (firstDate < endDate) {
            list.add(ComponentWeekDivisionModel(
                Timestamp(firstDate),
                Timestamp(Utils.addToDate(firstDate, 6)),
            ) {
                this.monthlyMonitoringCompanySituationViewModel.navigateToWeeklyMonitoringCompanySituation(
                    this.view,
                    bundleOf(
                        "currentUserData" to this.currentUserData,
                        "dateFilter" to ComponentWeekDivisionDateFilter(
                            Timestamp(initFilterDatetime),
                            Timestamp(endFilterDatetime)
                        )
                    )
                )
            })
            firstDate = Utils.addToDate(firstDate, 7)
        }

        this.binding.weeksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        this.binding.weeksRecyclerView.adapter = ComponentWeekDivisionAdapter(list)
        this.binding.weeksRecyclerView.setHasFixedSize(false)

    }

}
