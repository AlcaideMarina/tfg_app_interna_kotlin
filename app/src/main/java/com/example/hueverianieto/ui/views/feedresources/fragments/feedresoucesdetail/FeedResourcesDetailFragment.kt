package com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail

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
import com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail.HensResourcesDetailFragmentArgs
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FeedResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentFeedResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var feedResourcesData: FeedResourcesData
    private val feedResourcesDetailViewModel: FeedResourcesDetailViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args : FeedResourcesDetailFragmentArgs by navArgs()
        this.feedResourcesData = args.feedResourcesData
        this.currentUserData = args.currentUserData

        this.binding = FragmentFeedResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        setText()

        lifecycleScope.launchWhenStarted {
            feedResourcesDetailViewModel.getFeedResource(feedResourcesData.documentId!!)
            feedResourcesDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.feedResourcesDetailViewModel.feedResource.observe(this) { feedResurcesDataObserver ->
            if (feedResurcesDataObserver != null) {
                feedResourcesData = feedResurcesDataObserver
                setText()
            }
        }
    }

    override fun setListeners() {
        this.binding.cancelButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. Va a eliminar este ticket, y puede conllevar consecuencias para la empresa. ¿Está seguro de que quiere continuar?",
                "Atrás",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    this.feedResourcesDetailViewModel
                        .deleteFeedResources(feedResourcesData.documentId!!)
                    activity?.onBackPressedDispatcher?.onBackPressed()
                }
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as FeedResourcesDetailViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.setText("Eliminar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(feedResourcesData.expenseDatetime)
            this.kilosTextInputLayout.setText(feedResourcesData.kilos.toString())
            this.kilosTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setText(feedResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
        }
    }

}
