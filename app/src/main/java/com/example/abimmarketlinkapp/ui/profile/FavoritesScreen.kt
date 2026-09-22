package com.example.abimmarketlinkapp.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.data.local.ProductMockData
import com.example.abimmarketlinkapp.data.model.Product
import com.example.abimmarketlinkapp.ui.marketplace.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onProductClick: (Product) -> Unit,
    onAddToCart: (Product) -> Unit,
    onBack: () -> Unit
) {
    // Mocking products "added to chat" - picking a few from mock data
    val favorites = ProductMockData.products.filter { it.id == "f1" || it.id == "v2" || it.id == "fi3" }
    val related = ProductMockData.products.filter { it.category == favorites.firstOrNull()?.category && !favorites.contains(it) }.take(4)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF1B5E20))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Text(
                    "Products from Your Chats",
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            
            items(favorites) { product ->
                ProductCard(product, onClick = { onProductClick(product) }, onAddToCart = { onAddToCart(product) })
            }

            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Text(
                    "Recommended for You",
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
            }

            items(related) { product ->
                ProductCard(product, onClick = { onProductClick(product) }, onAddToCart = { onAddToCart(product) })
            }
            
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
