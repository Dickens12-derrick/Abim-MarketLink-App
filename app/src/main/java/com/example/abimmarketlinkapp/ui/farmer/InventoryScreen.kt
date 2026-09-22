package com.example.abimmarketlinkapp.ui.farmer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.CropItem
import com.example.abimmarketlinkapp.data.model.StockStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onCropClick: (String) -> Unit,
    onBack: () -> Unit
) {
    // Mock inventory data
    val crops = listOf(
        CropItem("c1", "Hass Avocado", Category.FRUITS, 200.0, 3500.0, 0, StockStatus.IN_STOCK, "2024-05-01"),
        CropItem("c2", "Kale (Sukuma)", Category.VEGETABLES, 15.0, 1000.0, 0, StockStatus.LOW_STOCK, "2024-05-10"),
        CropItem("c3", "Red Tomatoes", Category.VEGETABLES, 0.0, 2000.0, 0, StockStatus.OUT_OF_STOCK, "2024-04-25")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventory Management", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFF8F9FA)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(crops) { crop ->
                InventoryItemCard(crop = crop, onClick = { onCropClick(crop.id) })
            }
        }
    }
}

@Composable
fun InventoryItemCard(crop: CropItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(crop.name, fontSize = 18.sp, fontWeight = FontWeight.Black, color = Color(0xFF1A1C1E))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${crop.quantity.toInt()}kg - UGX ${crop.pricePerKg.toInt()}/kg", 
                    color = Color(0xFF42474E),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            
            val (statusText, statusColor, bgColor) = when (crop.stockStatus) {
                StockStatus.IN_STOCK -> Triple("In Stock", Color(0xFF2E7D32), Color(0xFFE8F5E9))
                StockStatus.LOW_STOCK -> Triple("Low Stock", Color(0xFFF57C00), Color(0xFFFFF3E0))
                StockStatus.OUT_OF_STOCK -> Triple("Out of Stock", Color(0xFFBA1A1A), Color(0xFFFFDAD6))
            }
            
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = bgColor
            ) {
                Text(
                    text = statusText,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    color = statusColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}
