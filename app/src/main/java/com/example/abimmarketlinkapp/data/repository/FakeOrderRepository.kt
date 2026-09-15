package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.Order
import com.example.abimmarketlinkapp.data.model.OrderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeOrderRepository : OrderRepository {

    private val mockOrders = listOf(
        Order(
            id = "ABM10987",
            productId = "1",
            productName = "Hass Avocados",
            quantity = 10.0,
            totalPrice = 45000.0,
            buyerName = "Nairobi Market Ltd",
            sellerName = "Abim Greens Farm",
            status = OrderStatus.CONFIRMED,
            date = "Oct 24, 2023",
            distance = "1.2km away"
        ),
        Order(
            id = "ABM0987",
            productId = "2",
            productName = "Fresh Kale",
            quantity = 5.0,
            totalPrice = 15000.0,
            buyerName = "Lakeside Cafe",
            sellerName = "Valley View Gardens",
            status = OrderStatus.PROCESSING,
            date = "Oct 23, 2023",
            distance = "0.8km away"
        )
    )

    override fun getOrders(): Flow<List<Order>> = flowOf(mockOrders)

    override fun getOrdersByStatus(status: OrderStatus): Flow<List<Order>> {
        return flowOf(mockOrders.filter { it.status == status })
    }

    override fun getOrderById(id: String): Flow<Order?> {
        return flowOf(mockOrders.find { it.id == id })
    }
}
