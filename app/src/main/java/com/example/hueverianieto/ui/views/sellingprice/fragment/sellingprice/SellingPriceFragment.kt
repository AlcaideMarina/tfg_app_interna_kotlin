package com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentSellingPriceBinding
import com.example.hueverianieto.ui.views.sellingprice.SellingPriceActivity

class SellingPriceFragment : BaseFragment() {

    private lateinit var binding : FragmentSellingPriceBinding
    private lateinit var currentUserData: InternalUserData
    private val sellingPriceViewModel: SellingPriceViewModel by viewModels()

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
        setButton()
        disableAllEditTexts()
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
        //TODO("Not yet implemented")
    }

    private fun setButton() {
        this.binding.modifyButton.setText("Modificar precios")
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

}
