package com.example.hueverianieto

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

    }

    override fun setListeners() {
        // TODO: sin implementar
    }

}