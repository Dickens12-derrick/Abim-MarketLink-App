package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    fun getProductsByCategory(category: Category): Flow<List<Product>>
    fun getProductById(id: String): Flow<Product?>
    fun searchProducts(query: String): Flow<List<Product>>
}
