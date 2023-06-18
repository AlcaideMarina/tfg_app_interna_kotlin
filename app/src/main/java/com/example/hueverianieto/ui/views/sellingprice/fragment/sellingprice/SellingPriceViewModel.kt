package com.example.hueverianieto.ui.views.sellingprice.fragment.sellingprice

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.domain.usecases.GetPricesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellingPriceViewModel @Inject constructor(
    val getPricesUseCase: GetPricesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(SellingPriceViewState())
    val viewState: StateFlow<SellingPriceViewState> get() = _viewState

    private var _eggPrices = MutableLiveData(EggPricesData())
    val eggPrices: LiveData<EggPricesData> get() = _eggPrices

    fun getPrices() {
        viewModelScope.launch {
            _viewState.value = SellingPriceViewState(isLoading = true)
            when (val result = getPricesUseCase()) {
                null -> {
                    _viewState.value = SellingPriceViewState(isLoading = false, error = true)
                    _eggPrices.value = EggPricesData()
                }
                else -> {
                    _viewState.value = SellingPriceViewState(isLoading = false)
                    _eggPrices.value = result
                }
            }
        }
    }

    fun navigateToModifySellingPrice(view: View?, bundle: Bundle) {
        view?.findNavController()
            ?.navigate(R.id.action_sellingPriceFragment_to_modifySellingPriceFragment, bundle)
            ?: Log.e(
                SellingPriceViewModel::class.simpleName,
                "Error en la navegación a modificar precios de venta"
            )
    }

}
