package com.example.hueverianieto.ui.views.main.fragments.settings

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.ui.views.changepassword.ChangePasswordActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    fun navigateToChangePassword(context: Context, internalUserData: InternalUserData) {
        val intent = Intent(context, ChangePasswordActivity::class.java)
        intent.putExtra("current_user_data", internalUserData)
        context.startActivity(intent)
    }

}
