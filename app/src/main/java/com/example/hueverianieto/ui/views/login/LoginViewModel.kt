package com.example.hueverianieto.ui.views.login

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.base.core.Event
import com.example.hueverianieto.data.models.local.LoginResponse
import com.example.hueverianieto.data.models.local.UserLoginData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.GetUserDataWithUidUseCase
import com.example.hueverianieto.domain.usecases.LoginUseCase
import com.example.hueverianieto.ui.views.main.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginUseCase: LoginUseCase,
    val getUserDataWithUidUseCase: GetUserDataWithUidUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(LoginViewState())
    val viewState: StateFlow<LoginViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(UserLoginData())
    val alertDialog: LiveData<UserLoginData> get() = _alertDialog

    private var _navigateToMainActivity = MutableLiveData<Event<Boolean>>()
    val navigateToMainActivity: LiveData<Event<Boolean>> get() = _navigateToMainActivity

    private var _currentUserData = MutableLiveData<InternalUserData>()
    val currentUserData: LiveData<InternalUserData> get() = _currentUserData

    private fun checkValidEmail(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun login(email: String, password: String) {
        if (checkValidEmail(email)) {
            viewModelScope.launch {
                _viewState.value = LoginViewState(isLoading = true)
                when (val result = loginUseCase(email, password)) {
                    LoginResponse.Error -> {
                        _alertDialog.value = UserLoginData(email, password, true)
                        _viewState.value = LoginViewState(false)
                    }
                    is LoginResponse.Success -> {
                        when (val internalUser = getUserDataWithUidUseCase(result.uid)) {
                            null -> {
                                _alertDialog.value = UserLoginData(email, password, true)
                                _viewState.value = LoginViewState(false)
                            }
                            else -> {
                                if (!internalUser.deleted) {
                                    _currentUserData.value = internalUser!!
                                    _navigateToMainActivity.value = Event(true)
                                } else {
                                    _alertDialog.value = UserLoginData(email, password, true)
                                    _viewState.value = LoginViewState(false)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            _viewState.value = LoginViewState(
                isValidEmail = checkValidEmail(email)
            )
        }
    }


    fun navigateToMainActivity(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
        (context as BaseActivity).finish()
    }

}
