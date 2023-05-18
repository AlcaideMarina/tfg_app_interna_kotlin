package com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.HomeUseCase
import javax.inject.Inject

class MonthlyMonitoringCompanySituationViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) {

    private fun navigateToWeeklyMonitoringCompanySituation(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_monthlyMonitoringCompanySituationFragment_to_weeklyMonitoringCompanySituationFragment, bundle)
            ?: Log.e(
                MonthlyMonitoringCompanySituationViewModel::class.java.simpleName,
                "Error a la navegación de seguimiento de situación de la empresa - vista semanal"
            )
    }

}