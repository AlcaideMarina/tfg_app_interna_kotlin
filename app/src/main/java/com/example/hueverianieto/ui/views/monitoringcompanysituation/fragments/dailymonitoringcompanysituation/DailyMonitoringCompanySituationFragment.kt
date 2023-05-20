package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.dailymonitoringcompanysituation

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
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.databinding.FragmentDailyFinalProductControlBinding
import com.example.hueverianieto.databinding.FragmentDailyMonitoringCompanySituationBinding
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DailyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var situationDatetime: Timestamp
    private val dailyMonitoringCompanySituationViewModel: DailyMonitoringCompanySituationViewModel by viewModels()

    private lateinit var monitoringCompanySituationData : MonitoringCompanySituationData

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
        this.dailyMonitoringCompanySituationViewModel.getDailyMonitoringCompanySituation(situationDatetime)
        disableFields()
        lifecycleScope.launchWhenStarted {
            dailyMonitoringCompanySituationViewModel.getDailyMonitoringCompanySituation(situationDatetime)
            dailyMonitoringCompanySituationViewModel.viewState.collect() { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.dailyMonitoringCompanySituationViewModel.monitoringCompanySituationData.observe(this) { data ->
            if (data != null) {
                monitoringCompanySituationData = data
            } else {
                monitoringCompanySituationData = MonitoringCompanySituationData(
                    brokenEggs = 0,
                    createdBy = null,
                    creationDatetime = null,
                    documentId = null,
                    hens = mapOf(
                        "alive" to 0,
                        "losses" to 0
                    ),
                    lEggs = mapOf(
                        "boxes" to 0,
                        "cartons" to 0,
                        "eggs" to 0
                    ),
                    mEggs = mapOf(
                        "boxes" to 0,
                        "cartons" to 0,
                        "eggs" to 0
                    ),
                    sEggs = mapOf(
                        "boxes" to 0,
                        "cartons" to 0,
                        "eggs" to 0
                    ),
                    situationDatetime = situationDatetime,
                    xlEggs = mapOf(
                        "boxes" to 0,
                        "cartons" to 0,
                        "eggs" to 0
                    )
                )
            }
            setFields()
            setButtons()
        }
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            this.dailyMonitoringCompanySituationViewModel.navigateToModifyDailyMonitoringCompanySituation(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "monitoringCompanySituationData" to monitoringCompanySituationData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        state as DailyMonitoringCompanySituationViewState
        this.binding.loadingComponent.isVisible = state.isLoading
    }

    private fun setButtons() {
        if (monitoringCompanySituationData.documentId == null) {
            this.binding.saveButton.setText("Añadir")
        } else {
            this.binding.saveButton.setText("Modificar")
        }
        this.binding.cancelButton.visibility = View.GONE
    }

    private fun setDate() {
        this.binding.dateTextView.text = Utils.parseTimestampToString(situationDatetime)
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

    private fun disableFields() {
        this.binding.xlBoxTextInputLayout.isEnabled = false
        this.binding.lBoxTextInputLayout.isEnabled = false
        this.binding.mBoxTextInputLayout.isEnabled = false
        this.binding.sBoxTextInputLayout.isEnabled = false
        this.binding.henLossesTextInputLayout.isEnabled = false
        this.binding.brokenEggsTextInputLayout.isEnabled = false
    }
}