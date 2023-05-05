package com.example.hueverianieto.ui.views.internalusers.fragments.newinternaluser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentNewInternalUserBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewInternalUserFragment : BaseFragment() {

    private lateinit var binding: FragmentNewInternalUserBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var currentUserData: InternalUserData

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
        this.alertDialog = HNModalDialog(requireContext())

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
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

}
