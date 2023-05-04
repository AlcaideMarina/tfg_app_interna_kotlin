package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.FragmentClientDetailBinding
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.ui.views.clients.fragments.modifyclient.ModifyClientViewState
import com.example.hueverianieto.ui.views.clients.fragments.newclient.NewClientFragment
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
import com.example.hueverianieto.utils.Utils.setPopUp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentClientDetailBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var clientData: ClientData
    private lateinit var currentUserData: InternalUserData
    private val clientDetailViewModel : ClientDetailViewModel by viewModels()

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
        // TODO: Habrá que controlar la parte de pedidos -> ordersLinearLayout.visibility = View.VISIBLE -> por ahora lo dejo seteado para que se muestre siempre

        clientDetailViewModel.getClientData(clientData.documentId!!)

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
                        null)
                } else {
                    setPopUp(
                        alertDialog,
                        requireContext(),
                        alertErrorOkData.title,
                        alertErrorOkData.text,
                        "De acuerdo",
                        null,
                        { alertDialog.cancel() },
                        null)
                }
            }
        }
        this.clientDetailViewModel.clientData.observe(this) {
            this.clientData = it
            setFieldTexts()
            setUserInfo()
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
            Utils.setPopUp(
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
    }

    override fun updateUI(state: BaseState) {
        with(state as ClientDetailViewState) {
            Log.v(ClientDetailFragment::class.java.simpleName, "Actualizando")
        }
    }

    private fun setButtons() {
        with(this.binding) {
            modifyButton.setText("Modificar")
            deleteClientButton.setText("Eliminar cliente")
            seeAllButton.setText("Ver todos")
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