package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.componentgridview.CustomGridLayoutManager
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.example.hueverianieto.utils.OrderUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var currentUserData : InternalUserData
    private val newOrderViewModel : NewOrderViewModel by viewModels()

    private val recyclerViewTitles = listOf(0, 7, 14, 21)
    private val recyclerViewSubtitles = listOf(1, 3, 4, 6, 8, 10, 11, 13, 15, 17, 18, 20, 22, 24, 25, 27)
    private val recyclerViewTextInputLayouts = listOf(2, 5, 9, 12, 16, 19, 23, 26)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )
        val args : NewOrderFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        return this.binding.root

    }

    override fun configureUI() {
        this.newOrderViewModel.getPrices()
        hideTextInputLayouts()
        disableTextInputLayouts()
        setRecyclerView(EggPricesData())

        lifecycleScope.launchWhenStarted {
            newOrderViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.newOrderViewModel.eggPrices.observe(this) {
            setRecyclerView(it)
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun hideTextInputLayouts() {
        with(this.binding) {
            orderIdTextView.visibility = View.GONE
            totalPriceTextLayout.visibility = View.GONE
            paidCheckedTextView.visibility = View.GONE
            orderDateTextLayout.visibility = View.GONE
            deliveryPersonTextLayout.visibility = View.GONE
            deliveryNoteTextLayout.visibility = View.GONE
            lotTextLayout.visibility = View.GONE
            deliveryDniTextLayout.visibility = View.GONE
            statusTextLayout.visibility = View.GONE
        }
    }

    private fun disableTextInputLayouts() {
        with(this.binding) {
            cifTextInputLayout.isEnabled = false
            directionTextLayout.isEnabled = false
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutPhone2.isEnabled = false
        }
    }

    private fun setRecyclerView(eggPricesData: EggPricesData) {

        val list = OrderUtils.getNewOrderGridModel(eggPricesData)

        val manager = CustomGridLayoutManager(this.context, 4)
        manager.setScrollEnabled(false)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (recyclerViewTitles.contains(position)) 4
                else if(recyclerViewTextInputLayouts.contains(position)) 2
                else 1
            }
        }

        this.binding.orderRecyclerView.layoutManager = manager
        this.binding.orderRecyclerView.adapter = HNGridTextAdapter(list)

    }

}
