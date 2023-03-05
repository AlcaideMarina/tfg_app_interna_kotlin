package com.example.hueverianieto

import android.content.Intent
import android.text.TextWatcher
import android.util.Log
import androidx.core.view.isEmpty
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun injection() {
        // TODO: sin implementar
    }

    override fun setUp() {
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        this.binding.loginButton.isEnabled = false

    }

    override fun setListeners() {
        // TODO: sin implementar
        // TODO: Listener textinputs - no funcionan
        this.binding.userTextInputLayout.setOnKeyListener {_, keyCode, keyEvent ->
            if (this.binding.userTextInputLayout.getText().isNotEmpty()
                && this.binding.passwordTextInputLayout.getText().isNotEmpty()) {
                this.binding.loginButton.isEnabled = true
            }
            true
        }

        this.binding.passwordTextInputLayout.setOnKeyListener {_, keyCode, keyEvent ->
            if (this.binding.passwordTextInputLayout.getText().isNotEmpty()
                && this.binding.userTextInputLayout.getText().isNotEmpty()) {
                this.binding.loginButton.isEnabled = true
            }
            true
        }
        this.binding.userTextInputLayout.addOnEditTextAttachedListener {


            if (this.binding.userTextInputLayout.getText().isNotEmpty()
                && this.binding.userTextInputLayout.getText().isNotEmpty()) {
                this.binding.loginButton.isEnabled = true
            }
        }
        this.binding.passwordTextInputLayout.addOnEditTextAttachedListener {
            if (this.binding.userTextInputLayout.getText().isNotEmpty()
                && this.binding.userTextInputLayout.getText().isNotEmpty()) {
                this.binding.loginButton.isEnabled = true
            }
        }
        // TODO: Listener del botón
        this.binding.loginButton.setOnClickListener {
            /*if (this.binding.userTextInputLayout.getText().isNotEmpty() &&
                this.binding.passwordTextInputLayout.getText().isNotEmpty()
            ) {
                this.binding.loginButton.isEnabled = true
            }*/
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}