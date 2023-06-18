package com.example.hueverianieto.ui.views.sellingprice.fragment.modifysellingprice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.domain.usecases.UpdateEggPricesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifySellingPriceViewModel @Inject constructor(
    val updateEggPricesUseCase: UpdateEggPricesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifySellingPriceViewState())
    val viewState: StateFlow<ModifySellingPriceViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    fun updateEggSellingPrices(eggPricesData: EggPricesData, userDocumentId: String) {
        viewModelScope.launch {
            _viewState.value = ModifySellingPriceViewState(isLoading = true)
            when (updateEggPricesUseCase(eggPricesData, userDocumentId)) {
                false -> {
                    _viewState.value = ModifySellingPriceViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        title = "Error",
                        text = "Se ha producido un error. Por favor, revise los datos e inténtelo de nuevo.",
                        finish = true
                    )
                }
                true -> {
                    _viewState.value = ModifySellingPriceViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        title = "Cambio registrado",
                        text = "Los precios de los productos se han cambiado correctamente.",
                        finish = true,
                        customCode = 0
                    )
                }
            }
        }
    }

}
