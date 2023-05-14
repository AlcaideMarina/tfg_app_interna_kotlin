package com.example.hueverianieto.ui.views.hensresouces.fragments.hensresourcesdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.HensResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ComponentTicketBinding
import com.example.hueverianieto.databinding.FragmentAllHensResourcesBinding
import com.example.hueverianieto.databinding.FragmentHensResourcesDetailBinding
import com.example.hueverianieto.domain.model.componentticket.ComponentTicketModel
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HensResourcesDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentHensResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var hensResourcesData: HensResourcesData

    private val hensResourcesDetailViewModel: HensResourcesDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (activity as BaseActivity).configNav(true)

        val args : HensResourcesDetailFragmentArgs by navArgs()
        this.hensResourcesData = args.hensResourcesData
        this.currentUserData = args.currentUserData

        this.binding = FragmentHensResourcesDetailBinding.inflate(
            inflater, container, false
        )
        return this.binding.root

    }

    override fun configureUI() {
        setButtons()
        setText()
    }

    override fun setObservers() {
        //TODO("Not yet implemented")
    }

    override fun setListeners() {
        this.binding.saveButton.setOnClickListener {
            this.hensResourcesDetailViewModel.navigateToModifyHensResources(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "hensResourcesData" to hensResourcesData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Modificar")
        this.binding.cancelButton.setText("Eliminar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text = Utils.parseTimestampToString(hensResourcesData.expenseDatetime)
            this.quantityTextInputLayout.setText(hensResourcesData.hensNumber.toString())
            this.quantityTextInputLayout.isEnabled = false
            this.totalPriceTextInputLayout.setText(hensResourcesData.totalPrice.toString())
            this.totalPriceTextInputLayout.isEnabled = false
        }
    }

}
