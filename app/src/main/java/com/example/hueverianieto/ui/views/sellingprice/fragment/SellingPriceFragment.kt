package com.example.hueverianieto.ui.views.sellingprice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentSellingPriceBinding
import com.example.hueverianieto.ui.views.sellingprice.SellingPriceActivity

class SellingPriceFragment : BaseFragment() {

    private lateinit var binding : FragmentSellingPriceBinding
    private lateinit var currentUserData: InternalUserData

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
        setButton()
        disableAllEditTexts()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
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

}
