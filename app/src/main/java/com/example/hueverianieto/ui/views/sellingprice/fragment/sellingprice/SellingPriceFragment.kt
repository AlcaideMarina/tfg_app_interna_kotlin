package com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentSellingPriceBinding
import com.example.hueverianieto.ui.views.sellingprice.SellingPriceActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellingPriceFragment : BaseFragment() {

    private lateinit var binding: FragmentSellingPriceBinding
    private lateinit var currentUserData: InternalUserData
    private val sellingPriceViewModel: SellingPriceViewModel by viewModels()
    private var eggPricesData: EggPricesData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        (activity as SellingPriceActivity).getToolbar().setNavigationOnClickListener {
            (activity as BaseActivity).goBackFragments()
        }
        this.currentUserData = (activity as SellingPriceActivity).currentUserData

        this.binding = FragmentSellingPriceBinding.inflate(
            inflater, container, false
        )

        return this.binding.root
    }

    override fun configureUI() {
        this.sellingPriceViewModel.getPrices()
        disableAllEditTexts()
        lifecycleScope.launchWhenStarted {
            sellingPriceViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.sellingPriceViewModel.eggPrices.observe(this) { eggPriceData ->
            this.eggPricesData = eggPriceData
            setEditTextInfo(eggPriceData)
        }
    }

    override fun setListeners() {
        this.binding.modifyButton.setOnClickListener {
            if (eggPricesData == null) {
                eggPricesData = EggPricesData(
                    0, 0, 0, 0, 0, 0, 0, 0
                )
            }
            this.sellingPriceViewModel.navigateToModifySellingPrice(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "eggPricesData" to eggPricesData
                )
            )
        }
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

    private fun disableAllEditTexts() {
        with(this.binding) {
            xlBoxEditText.isEnabled = false
            xlDozenEditText.isEnabled = false
            lBoxEditText.isEnabled = false
            lDozenEditText.isEnabled = false
            mBoxEditText.isEnabled = false
            mDozenEditText.isEnabled = false
            sBoxEditText.isEnabled = false
            sDozenEditText.isEnabled = false
        }
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
