package com.example.abimmarketlinkapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.model.Product
import com.example.abimmarketlinkapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class DashboardUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String? = null
)

class DashboardViewModel(private val productRepository: ProductRepository) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = productRepository.getAllProducts()
        .combine(MutableStateFlow(false)) { products, isLoading ->
            DashboardUiState(products = products.take(3), isLoading = isLoading)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState(isLoading = true))
}
