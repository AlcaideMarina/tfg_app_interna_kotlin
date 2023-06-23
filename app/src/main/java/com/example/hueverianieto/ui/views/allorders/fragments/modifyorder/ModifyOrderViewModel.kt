package com.example.hueverianieto.ui.views.allorders.fragments.modifyorder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.local.EggPricesData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllDeliveryPersonUseCase
import com.example.hueverianieto.domain.usecases.GetInternalUserWithIdUseCase
import com.example.hueverianieto.domain.usecases.GetPricesUseCase
import com.example.hueverianieto.domain.usecases.UpdateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModifyOrderViewModel @Inject constructor(
    val updateOrderUseCase: UpdateOrderUseCase,
    val getAllDeliveryPersonUseCase: GetAllDeliveryPersonUseCase,
    val getInternalUserWithIdUseCase: GetInternalUserWithIdUseCase,
    val getPricesUseCase: GetPricesUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyOrderViewState())
    val viewState: StateFlow<ModifyOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _deliveryPersonList = MutableLiveData<List<InternalUserData?>>()
    val deliveryPersonList: LiveData<List<InternalUserData?>> get() = _deliveryPersonList

    private var _deliveryPerson = MutableLiveData<InternalUserData?>()
    val deliveryPerson: LiveData<InternalUserData?> get() = _deliveryPerson

    private var _eggPrices = MutableLiveData(EggPricesData())
    val eggPrices: LiveData<EggPricesData> get() = _eggPrices

    fun updateOrder(clientDocumentId: String, orderData: OrderData) {
        viewModelScope.launch {
            _viewState.value = ModifyOrderViewState(isLoading = true)
            when (val result = updateOrderUseCase(
                clientDocumentId, orderData
            )) {
                false -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false, error = true)
                    _alertDialog.value = AlertOkData(
                        "Error",
                        "Se ha producido un error cuando se estaban actualizado los datos del pedido. Por favor, revise los datos e inténtelo de nuevo.",
                        true
                    )
                }
                true -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false, error = false)
                    _alertDialog.value = AlertOkData(
                        "Pedido actualizado",
                        "Los datos del pedido se han actualizado correctamente.",
                        true,
                        1
                    )
                }
            }
        }
    }

    fun getAllDeliveryPerson() {
        viewModelScope.launch {
            _viewState.value = ModifyOrderViewState(isLoading = true)
            when (val result = getAllDeliveryPersonUseCase()) {
                null -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false, error = true)
                }
                else -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false)
                    _deliveryPersonList.value = result!!
                }
            }
        }
    }

    fun getDeliveryPerson(documentId: String) {
        viewModelScope.launch {
            _viewState.value = ModifyOrderViewState(isLoading = true)
            when (val result = getInternalUserWithIdUseCase(documentId)) {
                null -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false)
                }
                else -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false)
                    _deliveryPerson.value = result

                }
            }
        }
    }

    fun getPrices() {
        viewModelScope.launch {
            _viewState.value = ModifyOrderViewState(isLoading = true)
            when (val result = getPricesUseCase()) {
                null -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false, error = true)
                    _eggPrices.value = EggPricesData()
                }
                else -> {
                    _viewState.value = ModifyOrderViewState(isLoading = false)
                    _eggPrices.value = result
                }
            }
        }
    }

}
