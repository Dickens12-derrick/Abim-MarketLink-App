package com.example.abimmarketlinkapp.data.model

import androidx.annotation.DrawableRes
import kotlinx.serialization.Serializable

@Serializable
data class CropItem(
    val id: String,
    val name: String,
    val category: Category,
    val quantity: Double, // in kg
    val pricePerKg: Double, // in UGX
    @DrawableRes val imageRes: Int,
    val stockStatus: StockStatus,
    val lastHarvestDate: String,
    val healthStatus: String = "Healthy"
)

enum class StockStatus {
    IN_STOCK, LOW_STOCK, OUT_OF_STOCK
}
