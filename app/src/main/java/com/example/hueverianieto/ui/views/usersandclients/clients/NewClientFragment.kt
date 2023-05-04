package com.example.hueverianieto.ui.views.usersandclients.clients

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Loading
class NewClientFragment : BaseFragment() {

    private lateinit var binding: FragmentNewClientBinding
    private lateinit var alertDialog: HNModalDialog

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

    override fun injection() {
        // TODO: Sin implementar
    }

    override fun configureUI() {

        this.alertDialog = HNModalDialog(requireContext())

        this.binding.let {
            it.saveButton.isEnabled = true
            it.saveButton.setText("Guardar")
            it.cancelButton.isEnabled = true
            it.cancelButton.setText("Eliminar")

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

            it.emailAccountTextInputLayout.isEnabled = false
            it.userAccountTextInputLayout.isEnabled = false
        }

    }

    override fun setObservers() {
        //
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
                    saveUser()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AllClientsActivity).configNav("Nuevo cliente")
        this.binding = FragmentNewClientBinding.inflate(
            inflater, container, false
        )
        return this.binding.root
    }

    /*private fun initRecyclerView() {
        this.binding.newClientClientFieldsRecyclerView.layoutManager = LinearLayoutManager(context)
        this.binding.newClientClientFieldsRecyclerView.adapter = TitleTextInputLayoutAdapter(fieldList)
        this.binding.newClientClientFieldsRecyclerView.isNestedScrollingEnabled = false

        this.binding.containerBorderCheckTitle.getBodyContainer().layoutManager = LinearLayoutManager(context)
        this.binding.containerBorderCheckTitle.getBodyContainer().adapter = TitleTextInputLayoutAdapter(ClientUtils.createUserToClientFields())
        this.binding.containerBorderCheckTitle.getBodyContainer().isNestedScrollingEnabled = false
    }*/

    private fun setPopUp(title: String, message: String, listener: OnClickListener) {

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

    private fun saveUser() : String {
        var nextIdStr = "0000000000"
        Firebase.firestore.collection("client_info")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents = task.result
                    nextIdStr = documents.size().toString()
                    while(nextIdStr.length < 10) {
                        nextIdStr = "0$nextIdStr"
                    }

                    // TODO: Guardar datos
                    val userData = ClientData(
                        cif,
                        city,
                        "TODO: created_by",
                        company,
                        false,
                        direction,
                        email,
                        accountEmail,
                        hasAccount,
                        nextIdStr,
                        listOf(
                            mapOf(namePhone1 to phone1.toLong()),
                            mapOf(namePhone2 to phone2.toLong())
                        ),
                        postalCode.toLong(),
                        province,
                        null,
                        accountUser,
                        null
                    )
                    Firebase.firestore
                        .collection("client_info")
                        .add(ClientUtils.parcelableToMap(userData))
                        .addOnSuccessListener {
                            Log.d(NewClientFragment::class.java.simpleName, "DocumentSnapshot successfully written!")
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
                            ){
                                alertDialog.cancel()
                            }
                        }
                }
            }
        return nextIdStr
    }
}