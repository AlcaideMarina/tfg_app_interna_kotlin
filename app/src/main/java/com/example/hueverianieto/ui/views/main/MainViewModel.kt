package com.example.hueverianieto.ui.views.main

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.base.BaseActivity
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.ui.views.login.LoginActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToLogin(context: Context, activity: MainActivity) {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity.finish()
    }

}

