package com.example.hueverianieto.ui.views.main.fragments.farm

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.boxesandcartonsresources.BoxesAndCartonsActivity
import com.example.hueverianieto.ui.views.finalproductcontrol.FinalProductControlActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FarmViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToFinalProductControl(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, FinalProductControlActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
    }

}
