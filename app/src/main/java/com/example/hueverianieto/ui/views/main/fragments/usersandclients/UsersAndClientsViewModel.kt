package com.example.hueverianieto.ui.views.main.fragments.usersandclients

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.clients.AllClientsActivity
import com.example.hueverianieto.ui.views.usersandclients.users.AllInternalUsersActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersAndClientsViewModel @Inject constructor(val homeUseCase: HomeUseCase) : ViewModel() {

    fun navigateToAllClientsActivity(context: Context, internalUserData: InternalUserData) {
        val intent = Intent(context, AllClientsActivity::class.java)
        intent.putExtra("current_user_data", internalUserData)
        context.startActivity(intent)
    }

    fun navigateToAllInternalUsersActivity(context: Context, internalUserData: InternalUserData) {
        val intent = Intent(context, AllInternalUsersActivity::class.java)
        intent.putExtra("current_user_data", internalUserData)
        context.startActivity(intent)
    }

}