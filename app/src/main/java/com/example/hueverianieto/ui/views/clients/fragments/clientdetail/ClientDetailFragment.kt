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
import com.example.hueverianieto.ui.views.clients.fragments.newclient.NewClientFragment
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
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
        setFieldTexts()
        setUserInfo()

    }

    override fun setObservers() {
        // Not necessary
    }

    override fun setListeners() {
        // TODO: Listeners de los botones - El resto es estático
        this.binding.modifyButton.setOnClickListener {
            this.clientDetailViewModel.navigateToModifyClient(
                this.view,
                bundleOf(
                    "clientData" to clientData,
                    "currentUserData" to currentUserData
                )
            )
        }
    }

    override fun updateUI(state: BaseState) {
        // Not necessary
    }

    private fun setButtons() {
        with(this.binding) {
            modifyButton.setText("Modificar")
            deleteClientButton.setText("Eliminar cliente")
            deleteUserButton.setText("Eliminar usuario")
            seeAllButton.setText("Ver todos")
        }
    }

    private fun setFieldTexts() {

        val phone1 = clientData.phone[0].entries.iterator().next()
        val phone2 = clientData.phone[1].entries.iterator().next()

        with(this.binding) {
            companyTextInputLayout.setInputText(clientData.company)
            companyTextInputLayout.isEnabled = false
            directionTextInputLayout.setInputText(clientData.direction)
            directionTextInputLayout.isEnabled = false
            cityTextInputLayout.setInputText(clientData.city)
            cityTextInputLayout.isEnabled = false
            provinceTextInputLayout.setInputText(clientData.province)
            provinceTextInputLayout.isEnabled = false
            postalCodeTextInputLayout.setInputText(clientData.postalCode.toString())
            postalCodeTextInputLayout.isEnabled = false
            cifTextInputLayout.setInputText(clientData.cif)
            cifTextInputLayout.isEnabled = false
            emailTextInputLayout.setInputText(clientData.email)
            emailTextInputLayout.isEnabled = false
            phoneTextInputLayoutPhone1.setInputText(phone1.value.toString())
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutName1.setInputText(phone1.key)
            phoneTextInputLayoutName1.isEnabled = false
            phoneTextInputLayoutPhone2.setInputText(phone2.value.toString())
            phoneTextInputLayoutPhone2.isEnabled = false
            phoneTextInputLayoutName2.setInputText(phone2.key)
            phoneTextInputLayoutName2.isEnabled = false
        }
    }

    private fun setUserInfo() {
        with(this.binding) {
            checkedTextView.isEnabled = false
            emailAccountTextInputLayout.isEnabled = false
            emailAccountTextInputLayout.setInputText(clientData.email ?: "")
            userAccountTextInputLayout.isEnabled = false
            userAccountTextInputLayout.setInputText(clientData.user ?: "")
            deleteUserButton.isVisible = clientData.hasAccount
        }
    }
}