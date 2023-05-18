package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.HomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MonthlyMonitoringCompanySituationViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToWeeklyMonitoringCompanySituation(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_monthlyMonitoringCompanySituationFragment_to_weeklyMonitoringCompanySituationFragment, bundle)
            ?: Log.e(
                MonthlyMonitoringCompanySituationViewModel::class.java.simpleName,
                "Error a la navegación de seguimiento de situación de la empresa - vista semanal"
            )
    }

}