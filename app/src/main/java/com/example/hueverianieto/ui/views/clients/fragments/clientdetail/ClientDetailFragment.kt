package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


    private var company : String = ""
    private var direction : String = ""
    private var city : String = ""
    private var province : String = ""
    private var postalCode : String = ""
    private var cif : String  = ""
    private var email : String = ""
    private var phone1 : String = ""
    private  var namePhone1 : String = ""
    private var phone2 : String = ""
    private var namePhone2 : String = ""
    private var hasAccount : Boolean = false
    private var accountEmail : String? = ""
    private var accountUser : String? = ""


    override fun configureUI() {

        setButttons()
        // TODO: Habrá que controlar la parte de pedidos -> ordersLinearLayout.visibility = View.VISIBLE -> por ahora lo dejo seteado para que se muestre siempre
        setFieldTexts()
        setUserInfo()

    }

    override fun setObservers() {
        // TODO
    }

    override fun setListeners() {
        this.binding.checkedTextView.setOnClickListener {
            this.binding.checkedTextView.isChecked = !this.binding.checkedTextView.isChecked
            this.binding.userAccountTextInputLayout.isEnabled = this.binding.checkedTextView.isChecked
            this.binding.emailAccountTextInputLayout.isEnabled = this.binding.checkedTextView.isChecked
        }
        this.binding.deleteClientButton.setOnClickListener {  }
        this.binding.modifyButton.setOnClickListener {  }
    }

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

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

    private fun setPopUp(title: String, message: String, listener: View.OnClickListener) {

        // TODO: Close button
        alertDialog.show(
            requireContext(),
            ModalDialogModel(
                title,
                message,
                "De acuerdo",
                null,
                listener,
                null,
                true
            )
        )
    }

    private fun updateUser(): String {

        if (clientData.documentId != null) {
            val userData = ClientData(
                cif,
                city,
                this.clientData.createdBy,
                company,
                this.clientData.deleted,
                direction,
                email,
                accountEmail,
                hasAccount,
                this.clientData.id,
                listOf(
                    mapOf(namePhone1 to phone1.toLong()),
                    mapOf(namePhone2 to phone2.toLong())
                ),
                postalCode.toLong(),
                province,
                this.clientData.uid,
                accountUser,
                this.clientData.documentId
            )
            Firebase.firestore
                .collection("client_info")
                .document(this.clientData.documentId!!)
                .set(ClientUtils.parcelableToMap(userData))
                .addOnSuccessListener {
                    Log.d(
                        NewClientFragment::class.java.simpleName,
                        "DocumentSnapshot successfully written!"
                    )
                    setPopUp(
                        "Guardado correcto.",
                        "El cliente ha sido guardado correctamente en la base de datos."
                    ) {
                        alertDialog.cancel()
                        activity?.onBackPressedDispatcher?.onBackPressed()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(NewClientFragment::class.java.simpleName, "Error writing document", e)
                    setPopUp(
                        "Ha ocurrido un error.",
                        "Ha ocurrido un error en el poceso de creación del cliente en nuestra base de datos. Por favor, inténtelo de nuevo.\nError: ${e.message}"
                    ) {
                        alertDialog.cancel()
                    }
                }
        } else {
            setPopUp(
                "Error",
                "Ha habido un problema al guardar los cambios. Por favor, inténtelo de nuevo más tarde.",
            ) {
                alertDialog.cancel()
            }
        }



        return "0000000000"
    }

    private fun setButttons() {
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
            phoneTextInputLayoutPhone1.setInputText(phone1.key)
            phoneTextInputLayoutPhone1.isEnabled = false
            phoneTextInputLayoutName1.setInputText(phone1.value.toString())
            phoneTextInputLayoutName1.isEnabled = false
            phoneTextInputLayoutPhone2.setInputText(phone2.key)
            phoneTextInputLayoutPhone2.isEnabled = false
            phoneTextInputLayoutName2.setInputText(phone2.value.toString())
            phoneTextInputLayoutName2.isEnabled = false
        }
    }

    private fun setUserInfo() {
        with(this.binding) {
            checkedTextView.isEnabled = false
            emailAccountTextInputLayout.isEnabled = false
            emailAccountTextInputLayout.setInputText(clientData.emailAccount ?: "")
            userAccountTextInputLayout.isEnabled = false
            userAccountTextInputLayout.setInputText(clientData.user ?: "")
            deleteUserButton.isVisible = clientData.hasAccount
        }
    }
}