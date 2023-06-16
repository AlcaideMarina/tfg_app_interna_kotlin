package com.example.hueverianieto.ui.views.clientsbilling.fragments.montlybillingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentMontlyBillingDetailBinding
import com.example.hueverianieto.domain.model.billingmodel.BillingModel
import com.example.hueverianieto.ui.views.clientsbilling.ClientsBillingActivity
import com.example.hueverianieto.ui.views.main.MainActivity
import kotlin.properties.Delegates

class MonthlyBillingDetailFragment : BaseFragment() {

    private lateinit var binding : FragmentMontlyBillingDetailBinding
    private lateinit var currentUserData : InternalUserData
    private lateinit var billingModel : BillingModel
    private var thisMonth by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as ClientsBillingActivity).configNav(true)
        this.binding = FragmentMontlyBillingDetailBinding.inflate(
            inflater, container, false
        )
        val args : MonthlyBillingDetailFragmentArgs by navArgs()
        this.currentUserData = args.currentUserData
        this.billingModel = args.billingModel
        this.thisMonth = args.thisMonth
        return this.binding.root
    }

    // TODO: Falta por ocultar el texto cuando no es el mes actual
    // TODO: Falta redondear a dos decimales
    override fun configureUI() {
        with(this.binding) {
            currentMonthWarning.isVisible = thisMonth
            cashTextView.text = billingModel.paymentByCash.toString() + " €"
            receiptTextView.text = billingModel.paymentByReceipt.toString() + " €"
            transferTextView.text = billingModel.paymentByTransfer.toString() + " €"
            paidTextView.text = billingModel.paid.toString() + " €"
            toBePaidTextView.text = billingModel.toBePaid.toString() + " €"
            totalPriceTextView.text = billingModel.totalPrice.toString() + " €"
        }
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
}
