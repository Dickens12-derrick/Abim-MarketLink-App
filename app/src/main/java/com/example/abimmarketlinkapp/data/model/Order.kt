package com.example.abimmarketlinkapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Double,
    val unit: String,
    val pricePerUnit: Double,
    val deliveryFee: Double,
    val totalAmount: Double,
    val status: OrderStatus,
    val date: String,
    val deliveryAddress: String,
    val driverName: String? = null,
    val driverPhone: String? = null,
    val eta: String? = null
)

enum class OrderStatus {
    PENDING,
    CONFIRMED,
    PACKED_AND_READY,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
