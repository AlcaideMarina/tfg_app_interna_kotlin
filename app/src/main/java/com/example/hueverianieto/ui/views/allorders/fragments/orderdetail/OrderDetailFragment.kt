package com.example.hueverianieto.ui.views.allorders.fragments.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.databinding.FragmentOrderDetailBinding
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.components.componentgridview.CustomGridLayoutManager
import com.example.hueverianieto.ui.components.componentgridview.HNGridTextAdapter
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderDetailBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var orderData: OrderData
    private lateinit var clientData: ClientData
    private lateinit var currentUserData: InternalUserData
    private val orderDetailViewModel: OrderDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as BaseActivity).configNav(true)
        this.binding = FragmentOrderDetailBinding.inflate(
            inflater, container, false
        )

        val args: OrderDetailFragmentArgs by navArgs()
        this.orderData = args.orderData
        this.currentUserData = args.currentUserData

        this.alertDialog = HNModalDialog(requireContext())

        return this.binding.root

    }

    override fun configureUI() {
        this.orderDetailViewModel.getClientData(orderData.clientId)
        disableTextInputLayouts()
        setRecyclerView()
        setButtons()
        lifecycleScope.launchWhenStarted {
            orderDetailViewModel.getClientData(orderData.clientId)
            orderDetailViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }
    }

    override fun setObservers() {
        this.orderDetailViewModel.clientData.observe(this) { clientDataUseCase ->
            if (clientDataUseCase != null) {
                clientData = clientDataUseCase
                setTexts(clientData)
                orderDetailViewModel.getOrder(clientData.documentId!!, orderData.documentId!!)
            } else {
                Utils.setPopUp(
                    alertDialog,
                    requireContext(),
                    "Error",
                    "Se ha producido un error al intentar recuperar los datos del cliente. Por favor, inténtelo de nuevo.",
                    "De acuerdo",
                    null,
                    { alertDialog.cancel() },
                    null
                )
            }
        }
        this.orderDetailViewModel.alertDialog.observe(this) { alertOkData ->
            if (alertOkData.finish) {
                if (alertOkData.customCode == 0) {
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
                } else {
                    Utils.setPopUp(
                        alertDialog,
                        requireContext(),
                        alertOkData.title,
                        alertOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null
                    )
                }
            }
        }
        this.orderDetailViewModel.orderData.observe(this) {
            this.orderData = it!!
            setRecyclerView()
            setTexts(clientData)
            setButtons()
            if (orderData.deliveryPerson != null)
                this.orderDetailViewModel.getDeliveryPerson(orderData.deliveryPerson!!)
        }
        this.orderDetailViewModel.deliveryPerson.observe(this) { deliveryPerson ->
            if (deliveryPerson != null) {
                val text =
                    "${deliveryPerson.id} - ${deliveryPerson.name} ${deliveryPerson.surname}"
                this.binding.deliveryPersonAutoCompleteTextView.setText(text)
            }
        }
    }

    override fun setListeners() {
        this.binding.deleteButton.setOnClickListener {
            Utils.setPopUp(
                alertDialog,
                requireContext(),
                "Aviso importante",
                "Esta acción es irreversible. ¿Está seguro de que quiere eliminar el pedido?",
                "Cancelar",
                "Continuar",
                { alertDialog.cancel() },
                {
                    alertDialog.cancel()
                    orderDetailViewModel.deleteOrder(
                        clientData.documentId!!, orderData.documentId!!
                    )
                }
            )
        }
        this.binding.modifyButton.setOnClickListener {
            this.orderDetailViewModel.navigateToModifyOrder(
                this.view,
                bundleOf(
                    "currentUserData" to currentUserData,
                    "clientData" to clientData,
                    "orderData" to orderData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        with(state as OrderDetailViewState) {
            with(binding) {
                this.loadingComponent.isVisible = state.isLoading
            }
        }
    }

    private fun setButtons() {
        this.binding.modifyButtonText.text = "Modificar"
        this.binding.deleteButtonText.text = "Eliminar"
        if (this.orderData.status == Constants.orderStatus[R.string.cancelled]!!.toLong()) {
            this.binding.modifyButton.isEnabled = false
            this.binding.deleteButton.isEnabled = false
        }
        if (this.orderData.status == Constants.orderStatus[R.string.delivered]!!.toLong()) {
            this.binding.deleteButton.isEnabled = false
        }
    }

    private fun disableTextInputLayouts() {
        this.binding.companyTextInputLayout.isEnabled = false
        this.binding.directionTextInputLayout.isEnabled = false
        this.binding.cifTextInputLayout.isEnabled = false
        this.binding.phoneTextInputLayoutPhone1.isEnabled = false
        this.binding.phoneTextInputLayoutPhone2.isEnabled = false
        this.binding.totalPriceTextInputLayout.isEnabled = false
        this.binding.orderDateTextInputLayout.isEnabled = false
        this.binding.deliveryDateTextInputLayout.isEnabled = false
        this.binding.deliveryPersonTextInputLayout.isEnabled = false
        this.binding.deliveryNoteTextInputLayout.isEnabled = false
        this.binding.deliveryDniTextInputLayout.isEnabled = false
        this.binding.paidCheckedTextView.isEnabled = false
        this.binding.paymentMethodTextInputLayout.isEnabled = false
        this.binding.lotTextInputLayout.isEnabled = false
        this.binding.statusTextInputLayout.isEnabled = false
    }

    private fun setTexts(clientData: ClientData) {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        val priceStr: String
        if (orderData.totalPrice == null) {
            priceStr = requireContext().getString(R.string.price_pending)
            this.binding.euroText.visibility = View.GONE
        } else {
            priceStr = orderData.totalPrice.toString()
        }

        val statusApproxDeliveryDatetimeList = listOf<Long>(0, 1, 2)
        val deliveryDatetimeField: String =
            if (statusApproxDeliveryDatetimeList.contains(orderData.status)) {
                requireContext().getString(
                    Utils.getKey(
                        Constants.orderStatus, orderData.status.toInt()
                    )!!
                ) + " - " +
                        Utils.parseTimestampToString(orderData.approxDeliveryDatetime)
            } else if (orderData.status == (4).toLong()) {
                Utils.parseTimestampToString(orderData.deliveryDatetime) ?: ""
            } else if (orderData.status == (5).toLong()) {
                requireContext().getString(
                    Utils.getKey(
                        Constants.orderStatus, orderData.status.toInt()
                    )!!
                ) + " - " +
                        Utils.parseTimestampToString(orderData.deliveryDatetime)
            } else {
                Utils.parseTimestampToString(orderData.deliveryDatetime)
                    ?: Utils.parseTimestampToString(orderData.approxDeliveryDatetime)!!
            }

        with(this.binding) {
            orderIdTextView.text = orderData.orderId.toString()
            companyAutoCompleteTextView.setText(clientData.company)
            directionTextInputLayout.setText(clientData.direction)
            cifTextInputLayout.setText(clientData.cif)
            phoneTextInputLayoutPhone1.setText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setText(phone2.value.toString())
            totalPriceTextInputLayout.setText(priceStr)
            orderDateTextInputLayout.setText(
                Utils.parseTimestampToString(orderData.orderDatetime) ?: ""
            )
            deliveryDateTextInputLayout.setText(deliveryDatetimeField)
            deliveryNoteTextInputLayout.setText(orderData.deliveryNote?.toString() ?: "")
            deliveryDniTextInputLayout.setText(orderData.deliveryDni ?: "")
            paidCheckedTextView.isChecked = orderData.paid
            paymentMethodAutoCompleteTextView.setText(
                requireContext().getString(
                    Utils.getKey(Constants.paymentMethod, orderData.paymentMethod.toInt())!!
                )
            )
            lotTextInputLayout.setText(orderData.lot ?: "")
            statusAutoCompleteTextView.setText(
                requireContext().getString(
                    Utils.getKey(Constants.orderStatus, orderData.status.toInt())!!
                )
            )
        }

    }

    private fun setRecyclerView() {

        val bdOrderModel = OrderUtils.orderDataToBDOrderModel(orderData)

        with(this.binding) {
            this.xlDozenTextInputLayout.setText(bdOrderModel.xlDozenQuantity.toString())
            this.xlDozenPriceTextInputLayout.text = (bdOrderModel.xlDozenPrice ?: "-").toString() + " €/ud"
            this.xlDozenTextInputLayout.isEnabled = false
            this.xlBoxTextInputLayout.setText(bdOrderModel.xlBoxQuantity.toString())
            this.xlBoxPriceTextInputLayout.text = (bdOrderModel.xlBoxPrice ?: "-").toString() + " €/ud"
            this.xlBoxTextInputLayout.isEnabled = false

            this.lDozenTextInputLayout.setText(bdOrderModel.lDozenQuantity.toString())
            this.lDozenPriceTextInputLayout.text = (bdOrderModel.lDozenPrice ?: "-").toString() + " €/ud"
            this.lDozenTextInputLayout.isEnabled = false
            this.lBoxTextInputLayout.setText(bdOrderModel.lBoxQuantity.toString())
            this.lBoxPriceTextInputLayout.text = (bdOrderModel.lBoxPrice ?: "-").toString() + " €/ud"
            this.lBoxTextInputLayout.isEnabled = false

            this.mDozenTextInputLayout.setText(bdOrderModel.mDozenQuantity.toString())
            this.mDozenPriceTextInputLayout.text = (bdOrderModel.mDozenPrice ?: "-").toString() + " €/ud"
            this.mDozenTextInputLayout.isEnabled = false
            this.mBoxTextInputLayout.setText(bdOrderModel.mBoxQuantity.toString())
            this.mBoxPriceTextInputLayout.text = (bdOrderModel.mBoxPrice ?: "-").toString() + " €/ud"
            this.mBoxTextInputLayout.isEnabled = false

            this.sDozenTextInputLayout.setText(bdOrderModel.sDozenQuantity.toString())
            this.sDozenPriceTextInputLayout.text = (bdOrderModel.sDozenPrice ?: "-").toString() + " €/ud"
            this.sDozenTextInputLayout.isEnabled = false
            this.sBoxTextInputLayout.setText(bdOrderModel.sBoxQuantity.toString())
            this.sBoxPriceTextInputLayout.text = (bdOrderModel.sBoxPrice ?: "-").toString() + " €/ud"
            this.sBoxTextInputLayout.isEnabled = false
        }

    }

}
