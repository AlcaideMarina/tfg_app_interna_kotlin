package com.example.hueverianieto.ui.views.clients.fragments.newclient

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseFragment
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.databinding.FragmentNewClientBinding
import com.example.hueverianieto.utils.ClientUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
    private var accountUser : String? = ""
    

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

    override fun configureUI() {

        this.alertDialog = HNModalDialog(requireContext())

        this.binding.let {
            it.saveButton.setText("Guardar")
            it.cancelButton.setText("Cancelar")

            it.userAccountTextInputLayout.isEnabled = false

            /*it.companyTextInputLayout.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
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
            it.emailAccountTextInputLayout.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)*/
        }

    }

    override fun setObservers() {
        //
    }

    override fun setListeners() {
        this.binding.checkedTextView.setOnClickListener {
            this.binding.checkedTextView.isChecked = !this.binding.checkedTextView.isChecked
            this.binding.userAccountTextInputLayout.isEnabled = this.binding.checkedTextView.isChecked

        }
        this.binding.cancelButton.setOnClickListener { activity?.onBackPressedDispatcher?.onBackPressed() }
        this.binding.saveButton.setOnClickListener {

            this.binding.let {
                company = it.companyTextInputLayout.text.toString()
                direction = it.directionTextInputLayout.text.toString()
                city = it.cityTextInputLayout.text.toString()
                province = it.provinceTextInputLayout.text.toString()
                postalCode = it.postalCodeTextInputLayout.text.toString()
                cif = it.cifTextInputLayout.text.toString()
                email = it.emailTextInputLayout.text.toString()
                phone1 = it.phoneTextInputLayoutPhone1.text.toString()
                namePhone1 = it.phoneTextInputLayoutName1.text.toString()
                phone2 = it.phoneTextInputLayoutPhone2.text.toString()
                namePhone2 = it.phoneTextInputLayoutName1.text.toString()
                hasAccount = it.checkedTextView.isChecked
                accountUser = it.userAccountTextInputLayout.text.toString()
            }
            if (company != "" && direction != "" && city != "" && province != "" && postalCode.toLongOrNull() != null &&
                cif != "" && email != "" && phone1.toLongOrNull() != null && namePhone1 != "" && phone2 .toLongOrNull() != null &&
                namePhone2 != "") {
                // TODO: Checkear tipos
                if (!hasAccount) {
                    accountUser = null
                } else {
                    // TODO: Crear usuario en Auth
                    // TODO: Coger el uid
                }
                if (!Utils.isValidEmail(email)) {
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

    override fun updateUI(state: BaseState) {
        //TODO("Not yet implemented")
    }

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
                        hasAccount,
                        nextIdStr.toLong(),
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