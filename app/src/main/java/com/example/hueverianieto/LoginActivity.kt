package com.example.hueverianieto

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.components.HNModalDialog
import com.example.hueverianieto.data.ModalDialogModel
import com.example.hueverianieto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

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

        this.binding.userTextInputLayout.clearFocus()
        this.binding.passwordTextInputLayout.clearFocus()
    }

    override fun setListeners() {

        this.binding.userTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)
        this.binding.passwordTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)

        this.binding.loginButton.setOnClickListener {
            it.hideSoftInput()
            // TODO: bloquear scroll
            val email: String = this.binding.userTextInputLayout.getText()
            val password: String = this.binding.passwordTextInputLayout.getText()
            checkCredentials(email, password)
        }
    }

    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            this@LoginActivity.binding.loginButton.isEnabled =
                !(this@LoginActivity.binding.userTextInputLayout.getText().isEmpty()
                        || this@LoginActivity.binding.passwordTextInputLayout.getText().isEmpty())
            Log.v(TAG, "loginButton: " + this@LoginActivity.binding.loginButton.isEnabled.toString())
        }
    }

    private fun checkCredentials(email: String, password: String) {
        // TODO: Añadir corrutinas
        initProgressBar()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser

                    // TODO: pasar el user a la siguiente pantalla
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    closeProgressBar()
                    finish()
                } else {
                    closeProgressBar()
                    val errorMessage: String = if (task.exception != null) {
                        task.exception!!.message.toString()
                    } else {
                        "generic error"
                    }
                    setPopUp(errorMessage)

                }
            }
    }

    private fun initProgressBar() {
        Log.v(TAG, "inicio")
        this.binding.loadingComponent.visibility = View.VISIBLE
        this.binding.extraComponentsContainer.visibility = View.VISIBLE

        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun closeProgressBar() {
        Log.v(TAG, "FIN")
        this.binding.loadingComponent.visibility = View.GONE
        this.binding.extraComponentsContainer.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun setPopUp(errorMessage: String) {

        HNModalDialog(this).show(
            this,
            ModalDialogModel(
                "Vaya... ha habido un error",
                errorMap(errorMessage),
                "De acuerdo",
                null,
                {},
                null,
                true
            )
        )

    }

    private fun errorMap(errorMessage: String): String {
        return when(errorMessage) {
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
