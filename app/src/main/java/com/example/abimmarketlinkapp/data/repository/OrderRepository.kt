package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.Order
import com.example.abimmarketlinkapp.data.model.OrderStatus
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    fun getOrders(): Flow<List<Order>>
    fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>>
    fun getOrderById(id: String): Flow<Order?>
}
