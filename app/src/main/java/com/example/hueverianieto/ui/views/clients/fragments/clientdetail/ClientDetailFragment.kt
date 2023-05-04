package com.example.hueverianieto.ui.views.clients.fragments.clientdetail

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.ui.views.clients.fragments.newclient.NewClientFragment
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClientDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewClientBinding
    private lateinit var alertDialog: HNModalDialog
    private lateinit var clientData: ClientData

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

        this.alertDialog = HNModalDialog(requireContext())

        val args: ClientDetailFragmentArgs by navArgs()
        this.clientData = args.clientData

        this.binding.let {
            it.saveButton.isEnabled = true
            it.saveButton.setText("Guardar")
            it.cancelButton.isEnabled = true
            it.cancelButton.setText("Eliminar")

            it.ordersLinearLayout.visibility = View.VISIBLE

            var phones: MutableList<MutableMap<String, Long>> = mutableListOf(mutableMapOf())


            it.companyTextInputLayout.setInputText(this.clientData.company)
            it.directionTextInputLayout.setInputText(this.clientData.direction)
            it.cityTextInputLayout.setInputText(this.clientData.city)
            it.provinceTextInputLayout.setInputText(this.clientData.province)
            it.postalCodeTextInputLayout.setInputText(this.clientData.postalCode.toString())
            it.cifTextInputLayout.setInputText(this.clientData.cif)
            it.emailTextInputLayout.setInputText(this.clientData.email)
            it.phoneTextInputLayoutPhone1.setInputText("this.clientData.phone[0].values.")
            it.phoneTextInputLayoutName1.setInputText("this.clientData.phone[0].values.")
            it.phoneTextInputLayoutPhone2.setInputText("this.clientData.phone[0].values.")
            it.phoneTextInputLayoutName2.setInputText("this.clientData.phone[0].values.")
            it.emailAccountTextInputLayout.setInputText(this.clientData.company)

            it.companyTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            it.directionTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            it.cityTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            it.provinceTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
            it.postalCodeTextInputLayout.setInputType(InputType.TYPE_CLASS_NUMBER)
            it.cifTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
            it.emailTextInputLayout.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
            it.phoneTextInputLayoutPhone1.setInputType(InputType.TYPE_CLASS_PHONE)
            it.phoneTextInputLayoutName1.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            it.phoneTextInputLayoutPhone2.setInputType(InputType.TYPE_CLASS_PHONE)
            it.phoneTextInputLayoutName2.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS)
            it.emailAccountTextInputLayout.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)

            it.emailAccountTextInputLayout.isEnabled = this.clientData.hasAccount
            it.emailAccountTextInputLayout.setInputText(this.clientData.emailAccount ?: "")
            it.userAccountTextInputLayout.isEnabled = this.clientData.hasAccount
            it.userAccountTextInputLayout.setInputText(this.clientData.user ?: ""
            )
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
        this.binding.cancelButton.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        this.binding.saveButton.setOnClickListener {

            this.binding.let {
                company = it.companyTextInputLayout.getText()
                direction = it.directionTextInputLayout.getText()
                city = it.cityTextInputLayout.getText()
                province = it.provinceTextInputLayout.getText()
                postalCode = it.postalCodeTextInputLayout.getText()
                cif = it.cifTextInputLayout.getText()
                email = it.emailTextInputLayout.getText()
                phone1 = it.phoneTextInputLayoutPhone1.getText()
                namePhone1 = it.phoneTextInputLayoutName1.getText()
                phone2 = it.phoneTextInputLayoutPhone2.getText()
                namePhone2 = it.phoneTextInputLayoutName1.getText()
                hasAccount = it.checkedTextView.isChecked
                accountEmail = it.emailAccountTextInputLayout.getText()
                accountUser = it.userAccountTextInputLayout.getText()
            }
            if (company != "" && direction != "" && city != "" && province != "" && postalCode.toLongOrNull() != null &&
                cif != "" && email != "" && phone1.toLongOrNull() != null && namePhone1 != "" && phone2 .toLongOrNull() != null &&
                namePhone2 != "") {
                // TODO: Checkear tipos
                if (!hasAccount) {
                    accountEmail = null
                    accountUser = null
                } else {
                    // TODO: Crear usuario en Auth
                    // TODO: Coger el uid
                }
                if (!Utils.isValidEmail(email) || (accountEmail != null && !Utils.isValidEmail(accountEmail))) {
                    setPopUp(
                        "Email incorrecto",
                        "Hemos detectado que el formato del email introducido no es correcto. Por favor, revise los datos."
                    ) {
                        alertDialog.cancel()
                    }
                } else {
                    // TODO: Sacar el id
                    updateUser()
                }
            } else {
                setPopUp(
                    "Revise los datos",
                    "Hemos detectado que no se han rellenado todos los campos solicitados o que son incorrectos. Por favor, revise el formulario. Recuerde que los campos de usuario son obligatorios si el la casilla de verificación está marcada."
                ) {
                    alertDialog.cancel()
                }
            }

        }
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
        this.binding = FragmentNewClientBinding.inflate(
            inflater, container, false
        )
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