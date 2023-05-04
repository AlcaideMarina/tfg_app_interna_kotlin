package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // TODO: hay que controlar el botón de eliminar usuario

        with(this.binding) {
            modifyButton.setText("Modificar")
            deleteClientButton.setText("Eliminar cliente")
            deleteUserButton.setText("Eliminar usuario")
            seeAllButton.setText("Ver todos")

            ordersLinearLayout.visibility = View.VISIBLE

            val phone1 = clientData.phone[0].entries.iterator().next()
            val phone2 = clientData.phone[1].entries.iterator().next()

            companyTextInputLayout.setInputText(clientData.company)
            directionTextInputLayout.setInputText(clientData.direction)
            cityTextInputLayout.setInputText(clientData.city)
            provinceTextInputLayout.setInputText(clientData.province)
            postalCodeTextInputLayout.setInputText(clientData.postalCode.toString())
            cifTextInputLayout.setInputText(clientData.cif)
            emailTextInputLayout.setInputText(clientData.email)
            phoneTextInputLayoutPhone1.setInputText(phone1.key)
            phoneTextInputLayoutName1.setInputText(phone1.value.toString())
            phoneTextInputLayoutPhone2.setInputText(phone2.key)
            phoneTextInputLayoutName2.setInputText(phone2.value.toString())
            emailAccountTextInputLayout.setInputText(clientData.company)

            emailAccountTextInputLayout.isEnabled = clientData.hasAccount
            emailAccountTextInputLayout.setInputText(clientData.emailAccount ?: "")
            userAccountTextInputLayout.isEnabled = clientData.hasAccount
            userAccountTextInputLayout.setInputText(clientData.user ?: "")

        }

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
}