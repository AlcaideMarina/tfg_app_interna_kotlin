package com.example.hueverianieto.ui.views.sellingprice.fragment.modifysellingprice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentModifySellingPriceBinding

class ModifySellingPriceFragment  : BaseFragment() {

    private lateinit var binding : FragmentModifySellingPriceBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var eggPricesData: EggPricesData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        val args : ModifySellingPriceFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.eggPricesData = args.eggPricesData

        this.binding = FragmentModifySellingPriceBinding.inflate(
            inflater, container, false
        )

        return this.binding.root
    }

    override fun configureUI() {
        // TODO
    }

    override fun setObservers() {
        // TODO
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        // TODO
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
        private val TAG = ModifySellingPriceFragment::class.java.simpleName
    }

}
