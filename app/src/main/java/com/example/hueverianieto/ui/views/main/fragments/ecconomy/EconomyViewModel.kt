package com.example.hueverianieto.ui.views.main.fragments.ecconomy

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.clientsbilling.ClientsBillingActivity
import com.example.hueverianieto.ui.views.sellingprice.SellingPriceActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EconomyViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToClientsBilling(context: Context, internalUserData: InternalUserData) {
        val intent = Intent(context, ClientsBillingActivity::class.java)
        intent.putExtra("current_user_data", internalUserData)
        context.startActivity(intent)
    }

    fun navigateToSellingPrice(context: Context, internalUserData: InternalUserData) {
        val intent = Intent(context, SellingPriceActivity::class.java)
        intent.putExtra("current_user_data", internalUserData)
        context.startActivity(intent)
    }

}
