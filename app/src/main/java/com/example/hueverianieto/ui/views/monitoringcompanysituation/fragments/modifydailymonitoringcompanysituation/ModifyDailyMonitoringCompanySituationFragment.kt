package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.modifydailymonitoringcompanysituation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.MonitoringCompanySituationData
import com.example.hueverianieto.databinding.FragmentDailyMonitoringCompanySituationBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.FarmUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyDailyMonitoringCompanySituationFragment : BaseFragment() {

    private lateinit var binding: FragmentDailyMonitoringCompanySituationBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var monitoringCompanySituationData: MonitoringCompanySituationData

    private lateinit var alertDialog: HNModalDialog
    private val modifyDailyMonitoringCompanySituationVewModel: ModifyDailyMonitoringCompanySituationVewModel by viewModels()

    // TODO: Falta el alertdialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())
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
        this.modifyDailyMonitoringCompanySituationVewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 0) {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        },
                        null
                    )
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            val data = MonitoringCompanySituationData(
                brokenEggs = (this.binding.brokenEggsTextInputLayout.text ?: 0).toString().toLong(),
                createdBy = this.monitoringCompanySituationData.createdBy ?: currentUserData.documentId!!,
                creationDatetime = this.monitoringCompanySituationData.creationDatetime ?: Timestamp.now(),
                documentId = this.monitoringCompanySituationData.documentId,
                hens = mapOf(
                    "alive" to (this.monitoringCompanySituationData.hens["alive"] ?: 0),         // Gallinas vivas en el momento de la creación
                    "losses" to (this.binding.henLossesTextInputLayout.text ?: 0).toString().toLong(),
                ),
                lEggs = mapOf(
                    "boxes" to (this.binding.lBoxTextInputLayout.text ?: 0).toString().toLong(),
                    "cartons" to (this.binding.lCartonsNumberTextView.text ?: 0).toString().toLong(),
                    "eggs" to (this.binding.lEggsNumberTextView.text ?: 0).toString().toLong(),
                ),
                mEggs = mapOf(
                    "boxes" to (this.binding.mBoxTextInputLayout.text ?: 0).toString().toLong(),
                    "cartons" to (this.binding.mCartonsNumberTextView.text ?: 0).toString().toLong(),
                    "eggs" to (this.binding.mEggsNumberTextView.text ?: 0).toString().toLong(),
                ),
                sEggs = mapOf(
                    "boxes" to (this.binding.sBoxTextInputLayout.text ?: 0).toString().toLong(),
                    "cartons" to (this.binding.sCartonsNumberTextView.text ?: 0).toString().toLong(),
                    "eggs" to (this.binding.sEggsNumberTextView.text ?: 0).toString().toLong(),
                ),
                this.monitoringCompanySituationData.situationDatetime,
                xlEggs = mapOf(
                    "boxes" to (this.binding.xlBoxTextInputLayout.text ?: 0).toString().toLong(),
                    "cartons" to (this.binding.xlCartonsNumberTextView.text ?: 0).toString().toLong(),
                    "eggs" to (this.binding.xlEggsNumberTextView.text ?: 0).toString().toLong(),
                ),
            )
            if (monitoringCompanySituationData.documentId == null) {
                this.modifyDailyMonitoringCompanySituationVewModel.addDailyMonitoringCompanySituation(
                    data)
            } else {
                // modificar
                this.modifyDailyMonitoringCompanySituationVewModel.updateDailyMonitoringCompanySituation(
                    data, this.monitoringCompanySituationData.documentId!!
                )
            }
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setDate() {
        this.binding.dateTextView.text = Utils.parseTimestampToString(monitoringCompanySituationData.situationDatetime)
    }

    private fun setFields() {
        this.binding.xlBoxTextInputLayout.setText((monitoringCompanySituationData.xlEggs["boxes"]
            ?: "").toString())
        this.binding.xlBoxTextInputLayout.addTextChangedListener(xlWatcher)
        this.binding.xlEggsNumberTextView.text = (monitoringCompanySituationData.xlEggs["eggs"]
            ?: "0").toString()
        this.binding.xlCartonsNumberTextView.text = (monitoringCompanySituationData.xlEggs["cartons"]
            ?: "0").toString()
        this.binding.lBoxTextInputLayout.setText((monitoringCompanySituationData.lEggs["boxes"]
            ?: "").toString())
        this.binding.lBoxTextInputLayout.addTextChangedListener(lWatcher)
        this.binding.lEggsNumberTextView.text = (monitoringCompanySituationData.lEggs["eggs"]
            ?: "0").toString()
        this.binding.lCartonsNumberTextView.text = (monitoringCompanySituationData.lEggs["cartons"]
            ?: "0").toString()
        this.binding.mBoxTextInputLayout.setText((monitoringCompanySituationData.mEggs["boxes"]
            ?: "").toString())
        this.binding.mBoxTextInputLayout.addTextChangedListener(mWatcher)
        this.binding.mEggsNumberTextView.text = (monitoringCompanySituationData.mEggs["eggs"]
            ?: "0").toString()
        this.binding.mCartonsNumberTextView.text = (monitoringCompanySituationData.mEggs["cartons"]
            ?: "0").toString()
        this.binding.sBoxTextInputLayout.setText((monitoringCompanySituationData.sEggs["boxes"]
            ?: "").toString())
        this.binding.sBoxTextInputLayout.addTextChangedListener(sWatcher)
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

    private val xlWatcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                val equalities = FarmUtils.getXLEggsEqualities(s.toString().toLong())
                this@ModifyDailyMonitoringCompanySituationFragment.binding.xlEggsNumberTextView.text =
                    equalities.eggs.toString()
                this@ModifyDailyMonitoringCompanySituationFragment.binding.xlCartonsNumberTextView.text =
                    equalities.cartons.toString()
            } else {
                this@ModifyDailyMonitoringCompanySituationFragment.binding.xlEggsNumberTextView.text =
                    "0"
                this@ModifyDailyMonitoringCompanySituationFragment.binding.xlCartonsNumberTextView.text =
                    "0"
            }
        }

    }

    private val lWatcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                val equalities = FarmUtils.getLMSEggsEqualities(s.toString().toLong())
                this@ModifyDailyMonitoringCompanySituationFragment.binding.lEggsNumberTextView.text =
                    equalities.eggs.toString()
                this@ModifyDailyMonitoringCompanySituationFragment.binding.lCartonsNumberTextView.text =
                    equalities.cartons.toString()
            } else {
                this@ModifyDailyMonitoringCompanySituationFragment.binding.lEggsNumberTextView.text =
                    "0"
                this@ModifyDailyMonitoringCompanySituationFragment.binding.lCartonsNumberTextView.text =
                    "0"
            }
        }

    }

    private val mWatcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                val equalities = FarmUtils.getLMSEggsEqualities(s.toString().toLong())
                this@ModifyDailyMonitoringCompanySituationFragment.binding.mEggsNumberTextView.text =
                    equalities.eggs.toString()
                this@ModifyDailyMonitoringCompanySituationFragment.binding.mCartonsNumberTextView.text =
                    equalities.cartons.toString()
            } else {
                this@ModifyDailyMonitoringCompanySituationFragment.binding.mEggsNumberTextView.text =
                    "0"
                this@ModifyDailyMonitoringCompanySituationFragment.binding.mCartonsNumberTextView.text =
                    "0"
            }
        }

    }

    private val sWatcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (s.toString() != "") {
                val equalities = FarmUtils.getLMSEggsEqualities(s.toString().toLong())
                this@ModifyDailyMonitoringCompanySituationFragment.binding.sEggsNumberTextView.text =
                    equalities.eggs.toString()
                this@ModifyDailyMonitoringCompanySituationFragment.binding.sCartonsNumberTextView.text =
                    equalities.cartons.toString()
            } else {
                this@ModifyDailyMonitoringCompanySituationFragment.binding.sEggsNumberTextView.text =
                    "0"
                this@ModifyDailyMonitoringCompanySituationFragment.binding.sCartonsNumberTextView.text =
                    "0"
            }
        }

    }

}
