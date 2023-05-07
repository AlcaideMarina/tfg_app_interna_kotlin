package com.example.hueverianieto.ui.views.allorders.fragments.neworder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.domain.usecases.GetPricesUseCase
import com.example.hueverianieto.ui.views.allorders.fragments.allorders.AllOrdersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewOrderViewModel @Inject constructor(
    val getPricesUseCase : GetPricesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(NewOrderViewState())
    val viewState: StateFlow<NewOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _eggPrices = MutableLiveData(EggPricesData())
    val eggPrices: LiveData<EggPricesData> get() = _eggPrices

    fun getPrices() {
        viewModelScope.launch {
            _viewState.value = NewOrderViewState(isLoading = true)
            when(val result = getPricesUseCase()) {
                null -> {
                    _viewState.value = NewOrderViewState(isLoading = false, error = true)
                    _eggPrices.value = EggPricesData()
                }
                else -> {
                    _viewState.value = NewOrderViewState(isLoading = false)
                    _eggPrices.value = result
                }
            }
        }
    }

}