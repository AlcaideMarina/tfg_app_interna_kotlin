package com.example.hueverianieto.ui.views.feedresources.fragments.modifyfeedresources

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFeedResourcesDetailBinding
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ModifyFeedResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var feedResourcesData: FeedResourcesData
    private val modifyFeedResourcesViewModel: ModifyFeedResourcesViewModel by viewModels()

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
        setButtons()
        setText()
        lifecycleScope.launchWhenStarted {
            modifyFeedResourcesViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        //TODO("Not yet implemented")
    }

    override fun updateUI(state: BaseState) {
        with(state as ModifyFeedResourcesViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(feedResourcesData.expenseDatetime)
            this.kilosTextInputLayout.setText(feedResourcesData.kilos.toString())
            this.totalPriceTextInputLayout.setText(feedResourcesData.totalPrice.toString())
        }
    }

}
