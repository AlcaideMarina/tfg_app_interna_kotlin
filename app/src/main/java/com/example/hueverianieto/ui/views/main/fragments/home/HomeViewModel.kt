package com.example.hueverianieto.ui.views.main.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hueverianieto.R
import com.example.hueverianieto.data.models.remote.OrderData
import com.example.hueverianieto.domain.usecases.GetAllDocumentsIdUseCase
import com.example.hueverianieto.domain.usecases.GetClientTodayDeliveriesUseCase
import com.example.hueverianieto.domain.usecases.GetClientTodayOrdersUseCase
import com.example.hueverianieto.domain.usecases.GetDailyMonitoringCompanySituationUseCase
import com.example.hueverianieto.utils.Constants
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getAllDocumentsIdUseCase: GetAllDocumentsIdUseCase,
    val getClientTodayOrdersUseCase: GetClientTodayOrdersUseCase,
    val getClientTodayDeliveriesUseCase: GetClientTodayDeliveriesUseCase,
    val getDailyMonitoringCompanySituationUseCase: GetDailyMonitoringCompanySituationUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState())
    val viewState: StateFlow<HomeViewState> get() = _viewState

    private val _todayOrdersNumber = MutableLiveData<Int>()
    val todayOrdersNumber: LiveData<Int> get() = _todayOrdersNumber

    private val _todayDeliveriesNumber = MutableLiveData<Int>()
    val todayDeliveriesNumber: LiveData<Int> get() = _todayDeliveriesNumber

    private val _mcsIsDone = MutableLiveData<Boolean>()
    val mcsIsDone: LiveData<Boolean> get() = _mcsIsDone

    private val _serviceGetTodayOrders = MutableLiveData<Boolean>()
    val serviceGetTodayOrders: LiveData<Boolean> get() = _serviceGetTodayOrders

    private val _serviceGetTodayDeliveries = MutableLiveData<Boolean>()
    val serviceGetTodayDeliveries: LiveData<Boolean> get() = _serviceGetTodayDeliveries

    private val _serviceGetTodayMCS = MutableLiveData<Boolean>()
    val serviceGetTodayMCS: LiveData<Boolean> get() = _serviceGetTodayMCS

    fun getTodayOrders() {
        viewModelScope.launch {
            _viewState.value = HomeViewState(isLoading = true)
            when (val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _todayOrdersNumber.value = 0
                    _serviceGetTodayOrders.value = true
                    _viewState.value = HomeViewState(isLoading = false, error = true)
                }
                listOf<String>() -> {
                    _todayOrdersNumber.value = 0
                    _serviceGetTodayOrders.value = true
                    _viewState.value = HomeViewState(isLoading = false)
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
                                        if (order != null && order.status.toInt() != Constants.orderStatus[R.string.cancelled]!!.toInt()) {
                                            orderList.add(order)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _todayOrdersNumber.value = orderList.size
                    _serviceGetTodayOrders.value = true
                    _viewState.value = HomeViewState(isLoading = false)
                }
            }
        }
    }

    fun getTodayDelivery() {
        viewModelScope.launch {
            _viewState.value = HomeViewState(isLoading = true)
            when (val resultQ1 = getAllDocumentsIdUseCase("client_info")) {
                null -> {
                    _todayDeliveriesNumber.value = 0
                    _serviceGetTodayDeliveries.value = true
                    _viewState.value = HomeViewState(isLoading = false, error = true)
                }
                listOf<String>() -> {
                    _todayDeliveriesNumber.value = 0
                    _serviceGetTodayDeliveries.value = true
                    _viewState.value = HomeViewState(isLoading = false)
                }
                else -> {
                    val clientIdList: List<String?> = resultQ1
                    val orderList: MutableList<OrderData?> = mutableListOf()
                    for (id in clientIdList) {
                        if (id != null) {
                            when (val resultQ2 = getClientTodayDeliveriesUseCase(id)) {
                                null -> {}
                                else -> {
                                    for (order in resultQ2) {
                                        if (order != null && order.status.toInt() != Constants.orderStatus[R.string.cancelled]!!.toInt()) {
                                            orderList.add(order)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    _todayDeliveriesNumber.value = orderList.size
                    _serviceGetTodayDeliveries.value = true
                    _viewState.value = HomeViewState(isLoading = false)
                }
            }
        }
    }


    fun getDailyMonitoringCompanySituation(timestamp: Timestamp) {
        viewModelScope.launch {
            _viewState.value = HomeViewState(isLoading = true)
            _mcsIsDone.value = getDailyMonitoringCompanySituationUseCase(timestamp) != null
            _serviceGetTodayMCS.value = true
            _viewState.value = HomeViewState(isLoading = false)
        }
    }

}
