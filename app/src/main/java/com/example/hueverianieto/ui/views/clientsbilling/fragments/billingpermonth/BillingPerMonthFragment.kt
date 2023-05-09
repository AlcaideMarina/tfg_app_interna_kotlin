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
import com.example.hueverianieto.ui.components.componentbillingpermonth.ComponentBillingPerMonthAdapter
import com.example.hueverianieto.ui.views.clientsbilling.ClientsBillingActivity
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillingPerMonthFragment : BaseFragment() {

    private lateinit var binding : FragmentBillingPerMonthBinding
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

        val args : BillingPerMonthFragmentArgs by navArgs()
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
                // TODO: Error
            } else {
                val billingList = mutableListOf<BillingContainerItemModel>()
                for (item in billingContainerList) {
                    if (item != null) {
                        var billingContainerItemModel = BillingContainerItemModel(
                            item
                        ) {
                            this.billingViewModel.navigateToMonthlyBillingDetail(
                                this.view,
                                bundleOf(
                                    "billingModel" to item.billingModel!!,
                                    "currentUserData" to currentUserData
                                )
                            )
                        }
                        billingList.add(billingContainerItemModel)
                    }
                }
                if (billingList.isEmpty()) {
                    // TODO: Vacío
                } else {
                    this.binding.billingRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.billingRecyclerView.adapter =
                        ComponentBillingPerMonthAdapter(billingList)
                    this.binding.billingRecyclerView.visibility = View.VISIBLE
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
                    if (state.error) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    } else if (state.isEmpty) {
                        //setPopUp(errorMap(Constants.loginBadFormattedEmailError))
                    }
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