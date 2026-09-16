package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.local.ProductMockData
import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeProductRepository @Inject constructor() : ProductRepository {

    private val mockProducts = ProductMockData.products

    override fun getAllProducts(): Flow<List<Product>> = flowOf(mockProducts)

    override fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return flowOf(mockProducts.filter { it.category == category })
    }

    override fun getProductById(id: String): Flow<Product?> {
        return flowOf(mockProducts.find { it.id == id })
    }

    override fun searchProducts(query: String): Flow<List<Product>> {
        return flowOf(mockProducts.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        })
    }
}
