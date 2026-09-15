package com.example.abimmarketlinkapp.ui.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.Product
import com.example.abimmarketlinkapp.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MarketplaceUiState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = Category.values().toList(),
    val selectedCategory: Category? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

class MarketplaceViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _selectedCategory = MutableStateFlow<Category?>(null)

    val uiState: StateFlow<MarketplaceUiState> = combine(
        productRepository.getAllProducts(),
        _searchQuery,
        _selectedCategory
    ) { products, query, category ->
        val filteredProducts = products.filter { product ->
            (category == null || product.category == category) &&
            (query.isEmpty() || product.name.contains(query, ignoreCase = true))
        }
        MarketplaceUiState(
            products = filteredProducts,
            searchQuery = query,
            selectedCategory = category
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MarketplaceUiState(isLoading = true))

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategorySelect(category: Category?) {
        _selectedCategory.value = category
    }
}
