package com.example.abimmarketlinkapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.abimmarketlinkapp.AbimMarketLinkApplication
import com.example.abimmarketlinkapp.data.AppContainer
import com.example.abimmarketlinkapp.ui.chat.ChatViewModel
import com.example.abimmarketlinkapp.ui.dashboard.DashboardViewModel
import com.example.abimmarketlinkapp.ui.marketplace.MarketplaceViewModel
import com.example.abimmarketlinkapp.ui.orders.OrdersViewModel
import com.example.abimmarketlinkapp.ui.profile.ProfileViewModel

object AppViewModelProvider {
    val Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
            val container = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as? AbimMarketLinkApplication).container
            return when {
                modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(container.dataStoreManager) as T
                modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(container.productRepository) as T
                modelClass.isAssignableFrom(MarketplaceViewModel::class.java) -> MarketplaceViewModel(container.productRepository) as T
                modelClass.isAssignableFrom(ChatViewModel::class.java) -> ChatViewModel(container.chatRepository) as T
                modelClass.isAssignableFrom(OrdersViewModel::class.java) -> OrdersViewModel(container.orderRepository) as T
                modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(container.dataStoreManager) as T
                else -> throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
