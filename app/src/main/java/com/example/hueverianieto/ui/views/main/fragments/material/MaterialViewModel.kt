package com.example.hueverianieto.ui.views.main.fragments.material

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.electricitywatergasresources.ElectricityWaterGasResourcesActivity
import com.example.hueverianieto.ui.views.feedresources.FeedResourcesActivity
import com.example.hueverianieto.ui.views.hensresouces.HensResourcesActivity
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MaterialViewModel @Inject constructor(
    homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToWorkersResources(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, WorkersResourcesActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
    }

    fun navigateToHensResources(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, HensResourcesActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
    }

    fun navigateToEWGResources(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, ElectricityWaterGasResourcesActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
    }

    fun navigateToFeedResources(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, FeedResourcesActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        context.startActivity(intent)
    }

}
