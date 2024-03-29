package com.example.hueverianieto.ui.views.clientsbilling.fragments.billingpermonth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentBillingPerMonthBinding
import com.example.hueverianieto.domain.model.billingcontaineritemmodel.BillingContainerItemModel
import com.example.hueverianieto.ui.components.componentbillingpermonth.billing.ComponentBillingPerMonthAdapter
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BillingPerMonthFragment : BaseFragment() {

    private lateinit var binding: FragmentBillingPerMonthBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var clientData: ClientData
    private val billingViewModel: BillingPerMonthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentBillingPerMonthBinding.inflate(
            inflater, container, false
        )

        val args: BillingPerMonthFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.clientData = args.clientData

        return this.binding.root
    }

    override fun configureUI() {
        this.billingViewModel.getBillingData(clientData.documentId!!)
        lifecycleScope.launchWhenStarted {
            billingViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.billingViewModel.billingContainerList.observe(this) { billingContainerList ->
            if (billingContainerList == null) {
                this.binding.billingRecyclerView.visibility = View.GONE
                this.binding.containerWaringNoBilling.visibility = View.VISIBLE
                this.binding.containerWaringNoBilling.setTitle("Error")
                this.binding.containerWaringNoBilling.setText("Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.")
            } else {
                val billingList = mutableListOf<BillingContainerItemModel>()
                for (item in billingContainerList) {
                    if (item != null) {
                        val calendarDataMonth = Calendar.getInstance()
                        calendarDataMonth.time = item.initDate.toDate()
                        var dataMonth = calendarDataMonth.get(Calendar.MONTH) + 1
                        val calendarThisMonth = Calendar.getInstance()
                        calendarThisMonth.time = Timestamp.now().toDate()
                        var thisMonth = calendarThisMonth.get(Calendar.MONTH) + 1
                        var billingContainerItemModel = BillingContainerItemModel(
                            item
                        ) {
                            this.billingViewModel.navigateToMonthlyBillingDetail(
                                this.view,
                                bundleOf(
                                    "billingModel" to item.billingModel!!,
                                    "currentUserData" to currentUserData,
                                    "thisMonth" to (dataMonth == thisMonth)
                                )
                            )
                        }
                        billingList.add(billingContainerItemModel)
                    }
                }
                if (billingList.isEmpty()) {
                    this.binding.billingRecyclerView.visibility = View.GONE
                    this.binding.containerWaringNoBilling.visibility = View.VISIBLE
                    this.binding.containerWaringNoBilling.setTitle("No hay registros")
                    this.binding.containerWaringNoBilling.setText("Aún no hay registros de facturación para el cliente seleccionado (${clientData.id} - {${clientData.company})")
                } else {
                    this.binding.billingRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.billingRecyclerView.adapter =
                        ComponentBillingPerMonthAdapter(billingList)
                    this.binding.billingRecyclerView.visibility = View.VISIBLE
                    this.binding.containerWaringNoBilling.visibility = View.GONE
                }
            }
        }
    }

    override fun setListeners() {
        // Not necessary
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as BillingPerMonthViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString(), e)
        }
    }

    companion object {
        private val TAG = BillingPerMonthFragment::class.java.simpleName
    }

}
