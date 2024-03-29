package com.example.hueverianieto.ui.views.feedresources.fragments.feedresoucesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.FeedResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentFeedResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

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

        val args: FeedResourcesDetailFragmentArgs by navArgs()
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
        this.feedResourcesDetailViewModel.feedResource.observe(this) { feedResourcesDataObserver ->
            if (feedResourcesDataObserver != null) {
                feedResourcesData = feedResourcesDataObserver
                setText()
            }
        }
        this.feedResourcesDetailViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    alertOkData.title,
                    alertOkData.text,
                    "De acuerdo",
                    null,
                    {
                        alertDialog.cancel()
                        (activity as BaseActivity).goBackFragments()
                    },
                    null
                )
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
                }
            )
        }
        this.binding.saveButton.setOnClickListener {
            this.feedResourcesDetailViewModel.navigateToModifyFeedResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "feedResourcesData" to feedResourcesData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as FeedResourcesDetailViewState) {
            binding.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        this.binding.saveButtonText.text = "Modificar"
        this.binding.cancelButtonText.text = "Eliminar"
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(feedResourcesData.expenseDatetime)
            this.kilosTextInputLayout.setText(feedResourcesData.kilos.toString())
            this.kilosTextInputLayout.isEnabled = false
            this.kilosTextInputLayout.setTextColor(requireContext().getColor(R.color.black_light_color_80))
            this.totalPriceTextInputLayout.setText(feedResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setTextColor(requireContext().getColor(R.color.black_light_color_80))
        }
    }

}
