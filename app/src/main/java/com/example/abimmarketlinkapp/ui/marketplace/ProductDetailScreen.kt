package com.example.abimmarketlinkapp.ui.marketplace

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product,
    onBackClick: () -> Unit,
    onAddToCart: (Product, Int) -> Unit,
    onContactSeller: (Product) -> Unit,
    onPlaceOrder: (Product) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Scaffold(
        bottomBar = {
            ProductDetailBottomBar(
                onPlaceOrder = { onPlaceOrder(product) },
                onContactSeller = { onContactSeller(product) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.Black)
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Black,
                            color = Color.Black
                        )
                        Text(
                            text = "UGX ${product.price.toInt()}/${product.unit}",
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF1B5E20),
                            fontWeight = FontWeight.Black
                        )
                    }
                    IconButton(onClick = { /* Toggle favorite */ }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = null, tint = Color(0xFF1B5E20))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE8F5E9)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${product.rating} (${product.reviewCount} Reviews)", fontSize = 12.sp, fontWeight = FontWeight.Black, color = Color(0xFF1B5E20))
                        }
                    }
                    if (product.isOrganic) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = "Organic",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = Color(0xFF2E7D32),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                InfoRow(label = "Farm", value = product.farmName)
                InfoRow(label = "Seller", value = product.sellerName)
                InfoRow(label = "Location", value = product.distance)
                InfoRow(label = "Available", value = "${product.availableQuantity} ${product.unit}")

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Quantity", fontWeight = FontWeight.Black, color = Color.Black)
                    QuantitySelector(
                        quantity = quantity,
                        onQuantityChange = { quantity = it }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = "$label: ", fontWeight = FontWeight.Black, color = Color.Black)
        Text(text = value, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color(0xFFF1F3F4), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp)
    ) {
        IconButton(onClick = { if (quantity > 1) onQuantityChange(quantity - 1) }) {
            Text(text = "-", fontWeight = FontWeight.Black, fontSize = 24.sp, color = Color(0xFF1B5E20))
        }
        Text(text = quantity.toString(), modifier = Modifier.padding(horizontal = 16.dp), fontWeight = FontWeight.Black, fontSize = 18.sp, color = Color.Black)
        IconButton(onClick = { onQuantityChange(quantity + 1) }) {
            Text(text = "+", fontWeight = FontWeight.Black, fontSize = 20.sp, color = Color(0xFF1B5E20))
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    onPlaceOrder: () -> Unit,
    onContactSeller: () -> Unit
) {
    Surface(
        shadowElevation = 16.dp,
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onContactSeller,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, Color(0xFF1B5E20))
            ) {
                Text(text = "Chat Seller", color = Color(0xFF1B5E20), fontWeight = FontWeight.Black)
            }
            Button(
                onClick = onPlaceOrder,
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
            ) {
                Text(text = "Place Order", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
