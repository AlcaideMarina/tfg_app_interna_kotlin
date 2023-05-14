package com.example.hueverianieto.ui.views.hensresouces.fragments.newhensresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewHensResourcesBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewHensResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentNewHensResourcesBinding
    private lateinit var currentUserData: InternalUserData
    private val newHensResourcesViewModel: NewHensResourcesViewModel by viewModels()

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args : NewHensResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData

        this.binding = FragmentNewHensResourcesBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    override fun configureUI() {
        setButtons()
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

    private fun setButtons() {
        with(this.binding) {
            this.cancelButton.setText("Cancelar")
            this.saveButton.setText("Guardar")
        }
    }

}
