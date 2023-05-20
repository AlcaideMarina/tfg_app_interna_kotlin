package com.example.hueverianieto.ui.views.stocs.fragments.stocks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.monitoringcompanysituation.fragments.monthlymonitoringcompanysituation.MonthlyMonitoringCompanySituationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    val homeUseCase: HomeUseCase
) : ViewModel() {

    fun navigateToHensStocks(view: View?, bundle: Bundle) {
        view?.findNavController()?.navigate(R.id.action_stocksFragment_to_hensStocksFragment, bundle)
            ?: Log.e(
                MonthlyMonitoringCompanySituationViewModel::class.java.simpleName,
                "Error a la navegación de seguimiento de situación de la empresa - vista semanal"
            )
    }

}
