package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.local.OrderContainerModel
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentClientDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentordercontainer.componentordercontainertopthree.HNOrderContainerTopThreeContainerAdapter
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils.setPopUp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentClientDetailBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var clientData: ClientData
    private lateinit var currentUserData: InternalUserData
    private val clientDetailViewModel: ClientDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)

        this.binding = FragmentClientDetailBinding.inflate(
            inflater, container, false
        )

        val args: ClientDetailFragmentArgs by navArgs()
        this.clientData = args.clientData
        this.currentUserData = args.currentUserData

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root
    }

    override fun configureUI() {

        setButtons()

        clientDetailViewModel.getClientData(clientData.documentId!!)
        clientDetailViewModel.getOrders(clientData.documentId!!)

        setFieldTexts()
        setUserInfo()

        lifecycleScope.launchWhenStarted {
            clientDetailViewModel.getClientData(clientData.documentId!!)
            clientDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.clientDetailViewModel.alertDialog.observe(this) { alertErrorOkData ->
            if (alertErrorOkData.finish) {
                if (alertErrorOkData.customCode == 0) {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        alertErrorOkData.title,
                        alertErrorOkData.text,
                        "De acuerdo",
                        null,
                        {
                            alertDialog.cancel()
                            (activity as BaseActivity).goBackFragments()
                        },
                        null
                    )
                } else {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        alertErrorOkData.title,
                        alertErrorOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
        this.clientDetailViewModel.clientData.observe(this) {
            this.clientData = it
            setFieldTexts()
            setUserInfo()
        }
        this.clientDetailViewModel.allOrderList.observe(this) { orderDataList ->
            if (orderDataList == null || orderDataList.isEmpty()) {
                this.binding.ordersLinearLayout.visibility = View.GONE
            } else {
                val orderList = mutableListOf<OrderContainerModel>()
                for (orderData in orderDataList) {
                    if (orderData != null &&
                        orderData.status != Constants.orderStatus[R.string.cancelled]!!.toLong()
                    ) {
                        val orderContainerModel = OrderContainerModel(
                            orderData.orderDatetime,
                            orderData.orderId!!,
                            orderData.clientId.toString() + " - " + orderData.company,
                            OrderUtils.getOrderSummary(OrderUtils.orderDataToBDOrderModel(orderData)),
                            orderData.totalPrice ?: -1,
                            orderData.status,
                            orderData.deliveryDni
                        ) {
                            this.clientDetailViewModel.navigateToOrderDetail(
                                this.view,
                                bundleOf(
                                    "orderData" to orderData,
                                    "currentUserData" to currentUserData
                                )
                            )
                        }
                        orderList.add(orderContainerModel)
                    }
                }
                if (orderList.isEmpty()) {
                    this.binding.orderRecyclerView.visibility = View.GONE
                } else {
                    this.binding.orderRecyclerView.layoutManager = LinearLayoutManager(context)
                    this.binding.orderRecyclerView.adapter =
                        HNOrderContainerTopThreeContainerAdapter(orderList)
                    this.binding.orderRecyclerView.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun setListeners() {
        this.binding.modifyButton.setOnClickListener {
            this.clientDetailViewModel.navigateToModifyClient(
                this.view,
                bundleOf(
                    "clientData" to clientData,
                    "currentUserData" to currentUserData
                )
            )
        }

        this.binding.deleteClientButton.setOnClickListener {
            setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. ¿Está seguro de que quiere eliminar el cliente?",
                "Cancelar",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    this.clientDetailViewModel.deleteClient(clientData.documentId!!)
                }
            )
        }

        this.binding.seeAllButton.setOnClickListener {
            this.clientDetailViewModel.navigateToAllClientOrders(
                this.view,
                bundleOf(
                    "clientData" to clientData,
                    "currentUserData" to currentUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        state as ClientDetailViewState
        with(binding) {
            this.loadingComponent.isVisible = state.isLoading
        }
    }

    private fun setButtons() {
        with(this.binding) {
            modifyButtonText.text = "Modificar"
            deleteClientButtonText.text = "Eliminar cliente"
            seeAllButtonText.text = "Ver todos"
        }
    }

    private fun setFieldTexts() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        with(this.binding) {
            companyTextInputLayout.setText(clientData.company)
            companyTextInputLayout.isEnabled = false
            directionTextInputLayout.setText(clientData.direction)
            directionTextInputLayout.isEnabled = false
            cityTextInputLayout.setText(clientData.city)
            cityTextInputLayout.isEnabled = false
            provinceTextInputLayout.setText(clientData.province)
            provinceTextInputLayout.isEnabled = false
            postalCodeTextInputLayout.setText(clientData.postalCode.toString())
            postalCodeTextInputLayout.isEnabled = false
            cifTextInputLayout.setText(clientData.cif)
            cifTextInputLayout.isEnabled = false
            emailTextInputLayout.setText(clientData.email)
            emailTextInputLayout.isEnabled = false
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutName1.setText(phone1.key)
            phoneTextInputLayoutName1.isEnabled = false
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
            phoneTextInputLayoutPhone2.isEnabled = false
            phoneTextInputLayoutName2.setText(phone2.key)
            phoneTextInputLayoutName2.isEnabled = false
        }
    }

    private fun setUserInfo() {
        with(this.binding) {
            checkedTextView.isEnabled = false
            checkedTextView.isChecked = clientData.hasAccount
            userAccountTextInputLayout.isEnabled = false
            userAccountTextInputLayout.setText(clientData.user ?: "")
        }
    }

    override fun onResume() {
        clientDetailViewModel.getClientData(clientData.documentId!!)
        super.onResume()
    }

}
