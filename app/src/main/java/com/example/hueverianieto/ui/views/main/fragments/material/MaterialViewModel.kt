package com.example.hueverianieto.ui.views.main.fragments.material

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.example.hueverianieto.domain.usecases.HomeUseCase
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

}
