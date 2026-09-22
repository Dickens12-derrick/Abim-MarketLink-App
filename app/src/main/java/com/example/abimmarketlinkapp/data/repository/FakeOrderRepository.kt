package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.Order
import com.example.abimmarketlinkapp.data.model.OrderStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeOrderRepository @Inject constructor() : OrderRepository {

    private val mockOrders = listOf(
        Order(
            id = "1",
            productId = "f1",
            productName = "Hass Avocados",
            quantity = 50.0,
            unit = "kg",
            pricePerUnit = 3500.0,
            deliveryFee = 5000.0,
            totalAmount = 180000.0,
            status = OrderStatus.OUT_FOR_DELIVERY,
            date = "2024-05-15",
            deliveryAddress = "Kampala Market, St. 12",
            driverName = "Robert",
            driverPhone = "+256 778 XXX XXX",
            eta = "11:30 AM"
        ),
        Order(
            id = "2",
            productId = "v2",
            productName = "Fresh Kale",
            quantity = 10.0,
            unit = "bunch",
            pricePerUnit = 1000.0,
            deliveryFee = 2000.0,
            totalAmount = 12000.0,
            status = OrderStatus.PENDING,
            date = "2024-05-14",
            deliveryAddress = "Wandegeya, Plot 4"
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
