package com.example.hueverianieto.ui.views.allorders.fragments.allorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllOrdersBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import com.example.hueverianieto.ui.views.main.MainActivity
import kotlin.properties.Delegates

class AllOrdersFragment : BaseFragment() {

    private lateinit var binding: FragmentAllOrdersBinding
    private lateinit var currentUserData: InternalUserData
    private var fromNewOrderFragment by Delegates.notNull<Boolean>()
    private lateinit var alertDialog: HNModalDialog
    private var isFirst = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity).configNav(true)
        this.currentUserData = (activity as AllOrdersActivity).currentUserData
        this.fromNewOrderFragment = (activity as AllOrdersActivity).fromNewOrderFragment

        this.binding = FragmentAllOrdersBinding.inflate(
            inflater, container, false
        )

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