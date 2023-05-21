package com.example.hueverianieto.ui.views.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllDocumentsIdUseCase
import com.example.hueverianieto.domain.usecases.GetClientTodayDeliveriesUseCase
import com.example.hueverianieto.domain.usecases.GetClientTodayOrdersUseCase
import com.example.hueverianieto.domain.usecases.HomeUseCase
import com.example.hueverianieto.ui.views.main.fragments.orderanddelivery.OrderAndDeliveryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAllDocumentsIdUseCase: GetAllDocumentsIdUseCase,
    val getClientTodayOrdersUseCase: GetClientTodayOrdersUseCase,
    val getClientTodayDeliveriesUseCase: GetClientTodayDeliveriesUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> get() = _viewState

    private val _todayOrdersNumber = MutableLiveData<Int>()
    val todayOrdersNumber: LiveData<Int> get() = _todayOrdersNumber

    private val _todayDeliveriesNumber = MutableLiveData<Int>()
    val todayDeliveriesNumber: LiveData<Int> get() = _todayDeliveriesNumber

    fun getTodayOrders() {
        viewModelScope.launch {
            _viewState.value = HomeViewState(isLoading = true)
            when(val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _viewState.value = HomeViewState(isLoading = false, error = true)
                    _todayOrdersNumber.value = 0
                }
                listOf<String>() -> {
                    _viewState.value = HomeViewState(isLoading = false)
                    _todayOrdersNumber.value = 0
                }
                else -> {
                    val clientIdList : List<String?> = resultQ1
                    val orderList : MutableList<OrderData?> = mutableListOf()
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
                    _viewState.value = HomeViewState(isLoading = false)
                    _todayOrdersNumber.value = orderList.size
                }
            }
        }
    }

    fun getTodayDelivery() {
        viewModelScope.launch {
            _viewState.value = HomeViewState(isLoading = true)
            when(val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _viewState.value = HomeViewState(isLoading = false, error = true)
                    _todayDeliveriesNumber.value = 0
                }
                listOf<String>() -> {
                    _viewState.value = HomeViewState(isLoading = false)
                    _todayDeliveriesNumber.value = 0
                }
                else -> {
                    val clientIdList : List<String?> = resultQ1
                    val orderList : MutableList<OrderData?> = mutableListOf()
                    for (id in clientIdList) {
                        if (id != null) {
                            when (val resultQ2 = getClientTodayDeliveriesUseCase(id)) {
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
                    _viewState.value = HomeViewState(isLoading = false)
                    _todayDeliveriesNumber.value = orderList.size
                }
            }
        }
    }

}
