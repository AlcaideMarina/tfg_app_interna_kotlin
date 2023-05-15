package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.newfinalproductcontrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFinalProductControlDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.google.firebase.Timestamp

class NewFinalProductControlFragment : BaseFragment() {

    private lateinit var binding: FragmentFinalProductControlDetailBinding
    private lateinit var currentUserData: InternalUserData

    private lateinit var alertDialog: HNModalDialog
    private lateinit var datetimeSelected: Timestamp

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: NewFinalProductControlFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentFinalProductControlDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        TODO("Not yet implemented")
    }

    override fun setObservers() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        TODO("Not yet implemented")
    }
}