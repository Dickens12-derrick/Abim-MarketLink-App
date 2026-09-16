package com.example.abimmarketlinkapp.ui.marketplace

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.abimmarketlinkapp.data.model.Product

@Composable
fun DiscoverScreen(
    viewModel: MarketplaceViewModel,
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        MarketplaceHeader(
            searchQuery = uiState.searchQuery,
            onSearchQueryChange = { viewModel.onSearchQueryChange(it) }
        )
        
        CategoryFilterSection(
            categories = uiState.categories,
            selectedCategory = uiState.selectedCategory,
            onCategorySelect = { viewModel.onCategorySelect(it) }
        )
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.products) { product ->
                ProductCard(
                    product = product,
                    onClick = { onProductClick(product) },
                    onAddToCart = { onAddToCart(product) }
                )
            }
        }
    }
}
