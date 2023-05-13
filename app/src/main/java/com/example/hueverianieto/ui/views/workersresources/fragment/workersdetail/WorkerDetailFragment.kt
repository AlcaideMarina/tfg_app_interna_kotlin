package com.example.hueverianieto.ui.views.workersresources.fragment.workersdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentAllClientsBinding
import com.example.hueverianieto.databinding.FragmentWorkersDetailBinding
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkerDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentWorkersDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var internalUserData: InternalUserData

    private val workerDetailViewModel: WorkerDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        val args: WorkerDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.internalUserData = args.internalUserData

        this.binding = FragmentWorkersDetailBinding
            .inflate(inflater, container, false)

        return this.binding.root
    }

    override fun configureUI() {
        this.setTexts()
        this.setButtons()
        lifecycleScope.launchWhenStarted {
            workerDetailViewModel.getInternalUser(internalUserData.documentId)
            workerDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            this.workerDetailViewModel.navigateToModifyWorker(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "internalUserData" to internalUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as WorkerDetailViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setTexts() {
        with(this.binding) {
            this.idTextView.text = internalUserData.id.toString()
            this.nameTextView.text = internalUserData.name
            this.surnameTextView.text = internalUserData.surname
            this.dniTextView.text = internalUserData.dni
            this.accountTextView.text = internalUserData.bankAccount
            this.positionTextView.text = resources.getString(Utils.getKey(
                Constants.roles, internalUserData.position.toInt())!!)
            this.salaryTextInputLayout.setText((internalUserData.salary ?: "").toString())
            this.salaryTextInputLayout.isEnabled = false
        }
    }

    private fun setButtons() {
        with(this.binding) {
            this.saveButton.setText("Modificar")
            this.cancelButton.visibility = View.GONE
        }
    }

}
