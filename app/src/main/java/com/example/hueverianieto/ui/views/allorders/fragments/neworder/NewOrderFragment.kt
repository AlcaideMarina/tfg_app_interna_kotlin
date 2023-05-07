package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOrderFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderDetailBinding
    private lateinit var currentUserData : InternalUserData

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
