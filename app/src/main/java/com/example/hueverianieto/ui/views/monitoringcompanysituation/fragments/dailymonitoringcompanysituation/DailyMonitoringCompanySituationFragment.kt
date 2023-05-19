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

    private var monitoringCompanySituationData : MonitoringCompanySituationData? = null

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
        setDate()
        setButtons()
        lifecycleScope.launchWhenStarted {
            dailyMonitoringCompanySituationViewModel.getDailyMonitoringCompanySituation(situationDatetime)
            dailyMonitoringCompanySituationViewModel.viewState.collect() { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.dailyMonitoringCompanySituationViewModel.monitoringCompanySituationData.observe(this) { data ->
            monitoringCompanySituationData = data
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
        if (monitoringCompanySituationData == null) {
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
        if (monitoringCompanySituationData != null) {
            this.binding.xlBoxTextInputLayout.setText((monitoringCompanySituationData?.xlEggs?.get("boxes")
                ?: "0").toString())
            this.binding.xlEggsNumberTextView.text = (monitoringCompanySituationData?.xlEggs?.get("eggs")
                ?: "0").toString()
            this.binding.xlCartonsNumberTextView.text = (monitoringCompanySituationData?.xlEggs?.get("cartons")
                ?: "0").toString()
            this.binding.lBoxTextInputLayout.setText((monitoringCompanySituationData?.lEggs?.get("boxes")
                ?: "0").toString())
            this.binding.lEggsNumberTextView.text = (monitoringCompanySituationData?.lEggs?.get("eggs")
                ?: "0").toString()
            this.binding.lCartonsNumberTextView.text = (monitoringCompanySituationData?.lEggs?.get("cartons")
                ?: "0").toString()
            this.binding.mBoxTextInputLayout.setText((monitoringCompanySituationData?.mEggs?.get("boxes")
                ?: "0").toString())
            this.binding.mEggsNumberTextView.text = (monitoringCompanySituationData?.mEggs?.get("eggs")
                ?: "0").toString()
            this.binding.mCartonsNumberTextView.text = (monitoringCompanySituationData?.mEggs?.get("cartons")
                ?: "0").toString()
            this.binding.sBoxTextInputLayout.setText((monitoringCompanySituationData?.sEggs?.get("boxes")
                ?: "0").toString())
            this.binding.sEggsNumberTextView.text = (monitoringCompanySituationData?.sEggs?.get("eggs")
                ?: "0").toString()
            this.binding.sCartonsNumberTextView.text = (monitoringCompanySituationData?.sEggs?.get("cartons")
                ?: "0").toString()
            this.binding.henLossesTextInputLayout.setText((monitoringCompanySituationData?.hens?.get("loses")
                ?: "0").toString())
            this.binding.brokenEggsTextInputLayout.setText((monitoringCompanySituationData?.brokenEggs
                ?: "0").toString())
        } else {
            this.binding.xlBoxTextInputLayout.setText("0")
            this.binding.xlEggsNumberTextView.text = "0"
            this.binding.xlCartonsNumberTextView.text = "0"
            this.binding.lBoxTextInputLayout.setText("0")
            this.binding.lEggsNumberTextView.text = "0"
            this.binding.lCartonsNumberTextView.text = ("0")
            this.binding.mBoxTextInputLayout.setText("0")
            this.binding.mEggsNumberTextView.text = "0"
            this.binding.mCartonsNumberTextView.text = "0"
            this.binding.sBoxTextInputLayout.setText("0")
            this.binding.sEggsNumberTextView.text = "0"
            this.binding.sCartonsNumberTextView.text = "0"
            this.binding.henLossesTextInputLayout.setText("0")
            this.binding.brokenEggsTextInputLayout.setText("0")
        }
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