package com.example.abimmarketlinkapp.data.model

import androidx.annotation.DrawableRes

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val unit: String,
    @DrawableRes val imageRes: Int,
    val farmName: String,
    val sellerName: String,
    val rating: Float,
    val reviewCount: Int,
    val distance: String,
    val category: Category,
    val isOrganic: Boolean,
    val description: String,
    val availableQuantity: Double
)

enum class Category {
    FRUITS, VEGETABLES, GRAINS, LIVESTOCK, FISH, POULTRY, OILS, DAIRY
}
