package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.weeklymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentWeeklyMonitoringCompanySituationBinding
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp

class WeeklyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentWeeklyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var initTimestamp: Timestamp
    private lateinit var endTimestamp: Timestamp

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