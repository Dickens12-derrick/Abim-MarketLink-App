package com.example.abimmarketlinkapp.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.model.Order
import com.example.abimmarketlinkapp.data.model.OrderStatus
import com.example.abimmarketlinkapp.data.repository.OrderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class OrdersUiState(
    val orders: List<Order> = emptyList(),
    val selectedStatus: OrderStatus? = null,
    val isLoading: Boolean = false
)

class OrdersViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _selectedStatus = MutableStateFlow<OrderStatus?>(OrderStatus.CONFIRMED)

    val uiState: StateFlow<OrdersUiState> = combine(
        orderRepository.getOrders(),
        _selectedStatus
    ) { orders, status ->
        val filteredOrders = if (status != null) {
            orders.filter { it.status == status }
        } else {
            orders
        }
        OrdersUiState(orders = filteredOrders, selectedStatus = status)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), OrdersUiState(isLoading = true))

    fun onStatusChange(status: OrderStatus?) {
        _selectedStatus.value = status
    }
}
