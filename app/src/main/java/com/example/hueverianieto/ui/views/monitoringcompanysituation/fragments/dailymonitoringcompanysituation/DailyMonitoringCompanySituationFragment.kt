package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.dailymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentDailyFinalProductControlBinding
import com.example.hueverianieto.databinding.FragmentDailyMonitoringCompanySituationBinding
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp

class DailyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var situationDatetime: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: DailyMonitoringCompanySituationFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.situationDatetime = args.date.situationDatetime

        this.binding = FragmentDailyMonitoringCompanySituationBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        // TODO: Llamada para conseguir el doc
            // TODO: Si el doc no existe -> seteamos todo a 0
            // TODO: Si el doc existe -> seteamos los valores
        setDate()
        setButtons()
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

    private fun setButtons() {
        // TODO: Si existe doc -> Modificar
        // TODO: Si no existe -> Añadir
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.visibility = View.GONE
    }

    private fun setDate() {
        this.binding.dateTextView.text = Utils.parseTimestampToString(situationDatetime)
    }
}