package com.example.hueverianieto.ui.views.main.fragments.material

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.workersresources.WorkersResourcesActivity
import javax.inject.Inject

class MaterialViewModel @Inject constructor(
    homeUseCase: HomeUseCase
) {

    fun navigateToWorkersResources(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, WorkersResourcesActivity::class.java)
        intent.putExtra("currennt_user_data", currentUserData)
        context.startActivity(intent)
    }

}
