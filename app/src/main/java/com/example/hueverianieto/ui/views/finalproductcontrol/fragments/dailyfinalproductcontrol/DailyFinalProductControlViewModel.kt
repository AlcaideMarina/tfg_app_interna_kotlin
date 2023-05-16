package com.example.hueverianieto.ui.views.finalproductcontrol.fragments.dailyfinalproductcontrol

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.internalusers.fragments.allinternalusers.AllInternalUsersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyFinalProductControlViewModel @Inject constructor(
    var homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToDailyDeletedFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_dailyFinalProductControlFragment_to_dailyDeletedFinalProductControlFragment, bundle)
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

    fun navigateToNewFPC(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_dailyFinalProductControlFragment_to_newFinalProductControlFragment, bundle)
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

    fun navigateToFPCDetail(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_dailyFinalProductControlFragment_to_finalProductControlDetailFragment, bundle)
            ?: Log.e(
                DailyFinalProductControlViewModel::class.simpleName,
                "Error en la navegación a FPC diario"
            )
    }

}
