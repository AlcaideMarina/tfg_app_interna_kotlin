package com.example.hueverianieto

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

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

        this.binding.loginButton.setText(resources.getString(R.string.login_button).uppercase())
        this.binding.loginButton.setTextBold(true)
        this.binding.loginButton.isEnabled = false       // TODO: Esto hay que cambiarlo para que inicialmente esté deshabilitado

    }

    override fun setListeners() {

        this.binding.userTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)
        this.binding.passwordTextInputLayout.getTextInputEditTextComponent().addTextChangedListener(watcher)

        // TODO: Listener del botón
        this.binding.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}
