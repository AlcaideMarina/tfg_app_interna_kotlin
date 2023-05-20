package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.modifydailymonitoringcompanysituation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.databinding.FragmentDailyMonitoringCompanySituationBinding
import com.example.hueverianieto.utils.Utils

class ModifyDailyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var monitoringCompanySituationData: MonitoringCompanySituationData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args: ModifyDailyMonitoringCompanySituationFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.monitoringCompanySituationData = args.monitoringCompanySituationData

        this.binding = FragmentDailyMonitoringCompanySituationBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setDate()
        setFields()
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

    private fun setDate() {
        this.binding.dateTextView.text = Utils.parseTimestampToString(monitoringCompanySituationData.situationDatetime)
    }

    private fun setFields() {
        this.binding.xlBoxTextInputLayout.setText((monitoringCompanySituationData.xlEggs["boxes"]
            ?: "0").toString())
        this.binding.xlEggsNumberTextView.text = (monitoringCompanySituationData.xlEggs["eggs"]
            ?: "0").toString()
        this.binding.xlCartonsNumberTextView.text = (monitoringCompanySituationData.xlEggs["cartons"]
            ?: "0").toString()
        this.binding.lBoxTextInputLayout.setText((monitoringCompanySituationData.lEggs["boxes"]
            ?: "0").toString())
        this.binding.lEggsNumberTextView.text = (monitoringCompanySituationData.lEggs["eggs"]
            ?: "0").toString()
        this.binding.lCartonsNumberTextView.text = (monitoringCompanySituationData.lEggs["cartons"]
            ?: "0").toString()
        this.binding.mBoxTextInputLayout.setText((monitoringCompanySituationData.mEggs["boxes"]
            ?: "0").toString())
        this.binding.mEggsNumberTextView.text = (monitoringCompanySituationData.mEggs["eggs"]
            ?: "0").toString()
        this.binding.mCartonsNumberTextView.text = (monitoringCompanySituationData.mEggs["cartons"]
            ?: "0").toString()
        this.binding.sBoxTextInputLayout.setText((monitoringCompanySituationData.sEggs["boxes"]
            ?: "0").toString())
        this.binding.sEggsNumberTextView.text = (monitoringCompanySituationData.sEggs["eggs"]
            ?: "0").toString()
        this.binding.sCartonsNumberTextView.text = (monitoringCompanySituationData.sEggs["cartons"]
            ?: "0").toString()
        this.binding.henLossesTextInputLayout.setText((monitoringCompanySituationData.hens["loses"]
            ?: "0").toString())
        this.binding.brokenEggsTextInputLayout.setText((monitoringCompanySituationData.brokenEggs).toString())
    }

    private fun setButtons() {
        if (monitoringCompanySituationData.documentId == null) {
            this.binding.saveButton.setText("Añadir")
        } else {
            this.binding.saveButton.setText("Modificar")
        }
        this.binding.cancelButton.visibility = View.GONE
    }
}