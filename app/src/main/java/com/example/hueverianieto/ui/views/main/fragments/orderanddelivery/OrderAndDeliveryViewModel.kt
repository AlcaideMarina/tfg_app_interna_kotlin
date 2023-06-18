package com.example.hueverianieto.ui.views.main.fragments.orderanddelivery

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.local.AlertOkData
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllDocumentsIdUseCase
import com.example.hueverianieto.domain.usecases.GetClientTodayOrdersUseCase
import com.example.hueverianieto.ui.views.allorders.AllOrdersActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderAndDeliveryViewModel @Inject constructor(
    val getAllDocumentsIdUseCase: GetAllDocumentsIdUseCase,
    val getClientTodayOrdersUseCase: GetClientTodayOrdersUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(OrderAndDeliveryViewState())
    val viewState: StateFlow<OrderAndDeliveryViewState> get() = _viewState

    private var _alertDialog = MutableLiveData(AlertOkData())
    val alertDialog: LiveData<AlertOkData> get() = _alertDialog

    private var _allOrderList = MutableLiveData<List<OrderData?>?>()
    val allOrderList: LiveData<List<OrderData?>?> get() = _allOrderList

    fun getTodayOrders() {
        viewModelScope.launch {
            _viewState.value = OrderAndDeliveryViewState(isLoading = true)
            when (val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _viewState.value = OrderAndDeliveryViewState(isLoading = false, error = true)
                }
                listOf<String>() -> {
                    _viewState.value = OrderAndDeliveryViewState(isLoading = false, isEmpty = true)
                }
                else -> {
                    val clientIdList: List<String?> = resultQ1
                    val orderList: MutableList<OrderData?> = mutableListOf()
                    for (id in clientIdList) {
                        if (id != null) {
                            when (val resultQ2 = getClientTodayOrdersUseCase(id)) {
                                null -> {}
                                else -> {
                                    for (order in resultQ2) {
                                        if (order != null) {
                                            orderList.add(order)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _viewState.value = OrderAndDeliveryViewState(isLoading = false)
                    _allOrderList.value = orderList
                }
            }
        }
    }

    fun navigateToAllOrdersActivity(context: Context, currentUserData: Parcelable) {
        val intent = Intent(context, AllOrdersActivity::class.java)
        intent.putExtra("current_user_data", currentUserData)
        intent.putExtra("from_new_order", false)
        context.startActivity(intent)
    }

}
