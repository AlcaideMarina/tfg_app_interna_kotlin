package com.example.hueverianieto.ui.views.feedresources.fragments.modifyfeedresources

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFeedResourcesDetailBinding
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyFeedResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var feedResourcesData: FeedResourcesData

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())
        val args: ModifyFeedResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.feedResourcesData = args.feedResourcesData

        this.binding = FragmentFeedResourcesDetailBinding
            .inflate(inflater, container, false)

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