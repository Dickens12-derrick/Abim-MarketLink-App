package com.example.abimmarketlinkapp.data

import android.content.Context
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import com.example.abimmarketlinkapp.data.repository.ChatRepository
import com.example.abimmarketlinkapp.data.repository.FakeChatRepository
import com.example.abimmarketlinkapp.data.repository.FakeOrderRepository
import com.example.abimmarketlinkapp.data.repository.FakeProductRepository
import com.example.abimmarketlinkapp.data.repository.OrderRepository
import com.example.abimmarketlinkapp.data.repository.ProductRepository

interface AppContainer {
    val productRepository: ProductRepository
    val chatRepository: ChatRepository
    val orderRepository: OrderRepository
    val dataStoreManager: DataStoreManager
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val productRepository: ProductRepository by lazy {
        FakeProductRepository()
    }
    override val chatRepository: ChatRepository by lazy {
        FakeChatRepository()
    }
    override val orderRepository: OrderRepository by lazy {
        FakeOrderRepository()
    }
    override val dataStoreManager: DataStoreManager by lazy {
        DataStoreManager(context)
    }
}
