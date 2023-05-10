package com.example.hueverianieto.ui.views.sellingprice.fragment.modifysellingprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifySellingPriceBinding
import com.example.hueverianieto.databinding.FragmentSellingPriceBinding
import com.example.hueverianieto.ui.views.sellingprice.SellingPriceActivity
import com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice.SellingPriceFragment
import com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice.SellingPriceViewModel
import com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice.SellingPriceViewState

class ModifySellingPriceFragment  : BaseFragment() {

    private lateinit var binding : FragmentModifySellingPriceBinding
    private lateinit var currentUserData: InternalUserData
    private val sellingPriceViewModel: SellingPriceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.binding = FragmentModifySellingPriceBinding.inflate(
            inflater, container, false
        )

        return this.binding.root
    }

    override fun configureUI() {
        this.sellingPriceViewModel.getPrices()
        setButton()
        lifecycleScope.launchWhenStarted {
            sellingPriceViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.sellingPriceViewModel.eggPrices.observe(this) { eggPriceData ->
            setEditTextInfo(eggPriceData)
        }
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        try {
            with(state as SellingPriceViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                }
            }
        } catch (e: java.lang.Exception) {
            Log.e(TAG, e.message.toString())
        }
    }

    private fun setButton() {
        this.binding.modifyButton.setText("Guardar")
    }

    private fun setEditTextInfo(eggPricesData: EggPricesData) {
        with(this.binding) {
            xlBoxEditText.setText(eggPricesData.xlBox.toString())
            xlDozenEditText.setText(eggPricesData.xlDozen.toString())
            lBoxEditText.setText(eggPricesData.lBox.toString())
            lDozenEditText.setText(eggPricesData.lDozen.toString())
            mBoxEditText.setText(eggPricesData.mBox.toString())
            mDozenEditText.setText(eggPricesData.mDozen.toString())
            sBoxEditText.setText(eggPricesData.sBox.toString())
            sDozenEditText.setText(eggPricesData.sDozen.toString())
        }
    }

    companion object {
        private val TAG = SellingPriceFragment::class.java.simpleName
    }

}
