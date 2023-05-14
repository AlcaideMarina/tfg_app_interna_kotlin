package com.example.hueverianieto.ui.views.hensresouces.fragments.newhensresources

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
        setFields()
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

    private fun setFields() {
        with(this.binding) {
            this.shedATextInputLayout.isEnabled = false
            this.shedBTextInputLayout.isEnabled = false
            this.quantityTextInputLayout.addTextChangedListener(watcherHensNumber)
        }
    }

    private val watcherHensNumber: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (this@NewHensResourcesFragment.binding.quantityTextInputLayout.text.toString().isEmpty()) {
                this@NewHensResourcesFragment.binding.shedATextInputLayout.setText("")
                this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = false
                this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText("")
                this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = false
            } else {
                try {
                    val hensNumber = s.toString().toInt()
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = true
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = true
                    if (hensNumber % 2 == 0) {
                        this@NewHensResourcesFragment.binding.shedATextInputLayout.setText(
                            (hensNumber / 2).toString())
                    } else {
                        this@NewHensResourcesFragment.binding.shedATextInputLayout.setText(
                            ((hensNumber / 2) + 1).toString())
                    }
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText(
                        ((hensNumber / 2)).toString())
                } catch (e: Exception) {
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.setText("")
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.setText("")
                    this@NewHensResourcesFragment.binding.shedATextInputLayout.isEnabled = false
                    this@NewHensResourcesFragment.binding.shedBTextInputLayout.isEnabled = false
                }
            }
        }

    }

}
