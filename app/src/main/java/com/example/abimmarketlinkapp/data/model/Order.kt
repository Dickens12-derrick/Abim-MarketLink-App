package com.example.abimmarketlinkapp.data.model

data class Order(
    val id: String,
    val productId: String,
    val productName: String,
    val quantity: Double,
    val totalPrice: Double,
    val buyerName: String,
    val sellerName: String,
    val status: OrderStatus,
    val date: String,
    val distance: String
)

enum class OrderStatus {
    PENDING, CONFIRMED, PROCESSING, COMPLETED, CANCELLED
}
