package com.example.hueverianieto.ui.views.boxesandcartonsresources.fragments.modifyboxesandcartonsresources

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.BoxesAndCartonsResourcesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentBoxesAndCartonsResourcesDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModifyBoxesAndCartonsResourcesFragment : BaseFragment() {

    private lateinit var binding: FragmentBoxesAndCartonsResourcesDetailBinding
    private lateinit var currentUserData: InternalUserData
    private lateinit var bcResourcesData: BoxesAndCartonsResourcesData

    private lateinit var alertDialog: HNModalDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.alertDialog = HNModalDialog(requireContext())

        val args: ModifyBoxesAndCartonsResourcesFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.bcResourcesData = args.bcResourcesData

        this.binding = FragmentBoxesAndCartonsResourcesDetailBinding
            .inflate(inflater, container, false)

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
        this.binding.cancelButton.setOnClickListener {
            it.hideSoftInput()
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

    private fun setButtons() {
        this.binding.saveButton.setText("Guardar")
        this.binding.cancelButton.setText("Cancelar")
    }

    private fun setText() {
        with(this.binding) {
            this.dateTextView.text =
                com.example.hueverianieto.utils.Utils.parseTimestampToString(bcResourcesData.expenseDatetime)
            this.boxesTextInputLayout.setText((bcResourcesData.order["box"] ?: "").toString())
            this.xlCartonsTextInputLayout.setText(
                (bcResourcesData.order["xl_carton"] ?: "").toString()
            )
            this.lCartonsTextInputLayout.setText(
                (bcResourcesData.order["l_carton"] ?: "").toString()
            )
            this.mCartonsTextInputLayout.setText(
                (bcResourcesData.order["m_carton"] ?: "").toString()
            )
            this.sCartonsTextInputLayout.setText(
                (bcResourcesData.order["s_carton"] ?: "").toString()
            )
            this.totalPriceTextInputLayout.setText(bcResourcesData.totalPrice.toString())
        }
    }

}
