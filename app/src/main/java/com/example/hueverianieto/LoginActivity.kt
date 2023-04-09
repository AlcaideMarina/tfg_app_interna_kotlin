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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.hueverianieto.base.BaseActivity
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

    }

    override fun setListeners() {

        this.binding.userTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)
        this.binding.passwordTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)

        // TODO: Listener del botón
        this.binding.loginButton.setOnClickListener {
            it.hideSoftInput()
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

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
                closeProgressBar()
            }
    }

    private fun initProgressBar() {
        Log.v(TAG, "inicio")
        this.binding.loadingComponent.visibility = View.VISIBLE
        this.binding.loadingContainerComponent.visibility = View.VISIBLE

        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    private fun closeProgressBar() {
        Log.v(TAG, "FIN")
        this.binding.loadingComponent.visibility = View.GONE
        this.binding.loadingContainerComponent.visibility = View.GONE
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}
