package com.example.hueverianieto.ui.views.internalusers.fragments.newinternaluser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewInternalUserBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewInternalUserFragment : BaseFragment() {

    private lateinit var binding: FragmentNewInternalUserBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var currentUserData: InternalUserData
    private var dropdownRoleItems : MutableList<String> = mutableListOf()
    private val newInternalUserViewModel : NewInternalUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        val args : NewInternalUserFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewInternalUserBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {

        setButtons()
        setDropdownRoleOptions()
        this.alertDialog = HNModalDialog(requireContext())

        lifecycleScope.launchWhenStarted {
            newInternalUserViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.roleAutoCompleteTextView.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                v.hideSoftInput()
            }
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as NewInternalUserViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setDropdownRoleOptions() {
        val values = Constants.roles.entries.iterator()
        for (v in values) {
            dropdownRoleItems.add(requireContext().getString(v.key))
        }

        this.binding.roleAutoCompleteTextView.setAdapter(
            ArrayAdapter(
                this.requireContext(), R.layout.component_dropdown_list_item,
                dropdownRoleItems
            ))

    }

}
