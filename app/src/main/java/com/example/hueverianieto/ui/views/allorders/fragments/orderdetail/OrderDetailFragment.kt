package com.example.hueverianieto.ui.views.allorders.fragments.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity

class OrderDetailFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var alertDialog : HNModalDialog
    private lateinit var orderData : OrderData
    private lateinit var currentUserData: InternalUserData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllOrdersActivity).configNav(true)
        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )

        val args : OrderDetailFragmentArgs by navArgs()
        this.orderData = args.orderData
        this.currentUserData = args.currentUserData

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        //TODO("Not yet implemented")
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
}