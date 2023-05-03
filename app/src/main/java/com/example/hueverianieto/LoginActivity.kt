package com.example.hueverianieto

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.components.HNModalDialog
import com.example.hueverianieto.data.components.ModalDialogModel
import com.example.hueverianieto.databinding.ActivityLoginBinding
import com.example.hueverianieto.utils.UserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var alertDialog: HNModalDialog

    override fun injection() {
        // TODO: sin implementar
    }

    override fun setUp() {
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
    }

    override fun configureUI() {

        this.binding.userTextInputLayout.setHintText(
            resources.getString(R.string.user_text_input_layout)
        )
        this.binding.passwordTextInputLayout.setHintText(
            resources.getString(R.string.password_text_input_layout)
        )
        this.binding.passwordTextInputLayout.setTransformationMethod(PasswordTransformationMethod.getInstance())

        this.binding.loginButton.setText(resources.getString(R.string.login_button).uppercase())
        this.binding.loginButton.setTextBold(true)
        this.binding.loginButton.isEnabled = false

        this.alertDialog = HNModalDialog(this)

    }

    override fun setListeners() {

        this.binding.userTextInputLayout.getTextInputEditTextComponent()
            .addTextChangedListener(watcher)
        this.binding.passwordTextInputLayout.getTextInputEditTextComponent()
            .addTextChangedListener(watcher)

        this.binding.loginButton.setOnClickListener {
            it.hideSoftInput()
            val email: String = this.binding.userTextInputLayout.getText()
            val password: String = this.binding.passwordTextInputLayout.getText()
            checkCredentials(email.trim(), password)
        }

    }

    private val watcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            this@LoginActivity.binding.loginButton.isEnabled =
                !(this@LoginActivity.binding.userTextInputLayout.getText().isEmpty()
                        || this@LoginActivity.binding.passwordTextInputLayout.getText().isEmpty())
        }

    }

    private fun checkCredentials(email: String, password: String) {

        initProgressBar()
// TODO: If email, password != ""
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    if (user != null) {
                        val db = Firebase.firestore
                        db.collection("user_info")
                            .whereEqualTo("uid", user.uid)
                            .whereEqualTo("deleted", false)
                            .limit(1)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val documents = task.result
                                    if (!documents.isEmpty) {
                                        // TODO: Control de nulos
                                        val document =
                                            documents.documents[0].data as MutableMap<String, Any?>?
                                        if (UserUtils.checkErrorMap(document) == null) {
                                            val data = document as MutableMap<String, Any?>
                                            val userData = UserUtils.mapToParcelable(
                                                data,
                                                documents.documents[0].id
                                            )
                                            val intent = Intent(this, MainActivity::class.java)
                                            intent.putExtra("current_user", userData)
                                            startActivity(intent)
                                            closeProgressBar()
                                            finish()
                                        } else {
                                            if (UserUtils.checkErrorMap(document) == "empty input map") {
                                                setPopUp("Ha habido un problema con tu usuario. Por favor, vuelve a intentarlo, y si el error persiste, ponte en contacto con nosotros.")
                                            }
                                        }
                                    }

                                } else {
                                    setPopUp("Ha habido un en el proceso de login. Por favor, inténtalo de nuevo.")
                                }
                            }.addOnFailureListener {
                                setPopUp("Ha habido un en el proceso de login. Por favor, inténtalo de nuevo.")
                            }
                    } else {
                        setPopUp("No hemos podido encontrar tu usuario en nuestra base de datos. Por favor, ponte en contacto con nosotros.")
                    }
                } else {
                    val errorMessage: String = if (task.exception != null) {
                        task.exception!!.message.toString()
                    } else {
                        "generic error"
                    }
                    setPopUp(errorMap(errorMessage))

                }
            }
        closeProgressBar()
    }

    private fun initProgressBar() {
        this.binding.loadingComponent.visibility = View.VISIBLE
        this.binding.extraComponentsContainer.visibility = View.VISIBLE
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun closeProgressBar() {
        this.binding.loadingComponent.visibility = View.GONE
        this.binding.extraComponentsContainer.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun setPopUp(errorMessage: String) {
        // TODO: Close button
        alertDialog.show(
            this,
            ModalDialogModel(
                "Vaya... ha habido un error",
                errorMessage,
                "De acuerdo",
                null,
                { alertDialog.cancel() },
                null,
                true
            )
        )
    }

    private fun errorMap(errorMessage: String): String {
        return when (errorMessage) {
            Constants.loginNetworkError -> "Parece que no tienes conexión a internet. Por favor, inténtalo más tarde."
            Constants.loginBadFormattedEmailError -> "El email introducido no tiene un formato válido. Por favor, revisa los datos y vuelve a intentarlo."
            Constants.loginNoUserRecordedError -> "El usuario no consta en nuestra base de datos. Por favor, revisa los datos y vuelve a intentarlo."
            Constants.loginInvalidPasswordError -> "El usuario y/o contraseña no son correctas. Por favor, revisa los datos y vuelve a intentarlo."
            else -> "Se ha producido un error inesperado. Por favor, inténtalo más tarde.\nError: $errorMessage"
        }
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}
