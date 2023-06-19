package com.example.hueverianieto.ui.views.login

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.hueverianieto.R
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.BaseState
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.databinding.ActivityLoginBinding
import com.example.hueverianieto.domain.model.modaldialog.ModalDialogModel
import com.example.hueverianieto.ui.components.HNModalDialog
import com.example.hueverianieto.ui.views.main.MainActivity
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.InternalUserUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var alertDialog: HNModalDialog
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var currentUserData: InternalUserData

    override fun setUp() {
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
    }

    override fun configureUI() {

        this.binding.userTextInputLayout.hint = "Correo"
        this.binding.userTextInputLayout.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        this.binding.passwordTextInputLayout.hint =
            resources.getString(R.string.password_text_input_layout)
        this.binding.passwordTextInputLayout.transformationMethod =
            PasswordTransformationMethod.getInstance()

        this.binding.loginButton.isEnabled = false

        this.alertDialog = HNModalDialog(this)

        lifecycleScope.launchWhenStarted {
            loginViewModel.viewState.collect { viewState ->
                updateUI(viewState)
            }
        }

    }

    override fun setListeners() {

        this.binding.userTextInputLayout.addTextChangedListener(watcher)
        this.binding.passwordTextInputLayout.addTextChangedListener(watcher)

        this.binding.loginButton.setOnClickListener { login(it) }

    }

    override fun setObservers() {
        loginViewModel.alertDialog.observe(this) { userLoginData ->
            if (userLoginData.error) {
                setPopUp("Ha habido un error en el login. Por favor, revisa los datos y comprueba que tengas acceso a internet.")
            }
        }
        loginViewModel.currentUserData.observe(this) { userData ->
            this.currentUserData = userData
        }
        loginViewModel.navigateToMainActivity.observe(this) { event ->
            event.getControlled()?.let {
                this.loginViewModel.navigateToMainActivity(this, currentUserData)
            }
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

    private fun login(view: View) {
        view.hideSoftInput()
        val email: String = this.binding.userTextInputLayout.text.toString()
        val password: String = this.binding.passwordTextInputLayout.text.toString()
        if (email != "" && password != "") {
            loginViewModel.login(email, password)
        }
    }

    private fun setPopUp(errorMessage: String) {
        alertDialog.show(
            this,
            ModalDialogModel(
                "Error",
                errorMessage,
                "De acuerdo",
                null,
                { alertDialog.cancel() },
                null,
                true
            )
        )
    }

    private fun updateUI(state: BaseState) {
        try {
            with(state as LoginViewState) {
                with(binding) {
                    this.loadingComponent.isVisible = state.isLoading
                    this.loginBaseContainer.isVisible = !state.isLoading
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
        }

    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
    }

}
