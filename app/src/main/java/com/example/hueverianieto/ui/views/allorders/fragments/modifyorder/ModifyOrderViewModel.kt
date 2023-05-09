package com.example.hueverianieto.ui.views.allorders.fragments.modifyorder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.ClientData
import com.example.hueverianieto.data.models.remote.InternalUserData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllDeliveryPersonUseCase
import com.example.hueverianieto.domain.usecases.UpdateOrderUseCase
import com.example.hueverianieto.ui.views.allorders.fragments.neworder.NewOrderViewState
import com.example.hueverianieto.ui.views.internalusers.fragments.modifyinternaluser.ModifyInternalUserViewState
import com.example.hueverianieto.utils.Constants
import com.example.hueverianieto.utils.InternalUserUtils
import com.example.hueverianieto.utils.OrderUtils
import com.example.hueverianieto.utils.Utils
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ModifyOrderViewModel @Inject constructor(
    val updateOrderUseCase : UpdateOrderUseCase,
    val getAllDeliveryPersonUseCase: GetAllDeliveryPersonUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(ModifyOrderViewState())
    val viewState: StateFlow<ModifyOrderViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _deliveryPersonList = MutableLiveData<List<InternalUserData?>>()
    val deliveryPersonList: LiveData<List<InternalUserData?>> get() = _deliveryPersonList


    fun updateOrder(clientDocumentId: String, orderData: OrderData) {
        viewModelScope.launch {
            _viewState.value = ModifyOrderViewState(isLoading = true)
            when (val result = updateOrderUseCase(
                clientDocumentId, orderData)) {
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
            when(val result = getAllDeliveryPersonUseCase()) {
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

}
