package com.example.abimmarketlinkapp.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.data.local.ProductMockData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCreationScreen(
    productId: String,
    onProceedToPayment: (String) -> Unit,
    onBack: () -> Unit
) {
    val product = ProductMockData.products.find { it.id == productId }
    var quantity by remember { mutableStateOf(10) }
    val itemTotal = (product?.price ?: 0.0) * quantity
    val deliveryFee = 5000.0
    val totalAmount = itemTotal + deliveryFee

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Order", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "${product?.name ?: "Produce"}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1C1E)
            )
            
            Text(
                text = "UGX ${product?.price?.toInt() ?: 0} / ${product?.unit ?: "unit"}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Quantity (${product?.unit ?: "kg"})", fontWeight = FontWeight.Medium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedIconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Text("-", fontSize = 24.sp)
                    }
                    Text("$quantity", modifier = Modifier.padding(horizontal = 16.dp), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    OutlinedIconButton(onClick = { quantity++ }) {
                        Text("+", fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Item Total", color = Color(0xFF42474E))
                        Text("UGX ${itemTotal.toInt()}", fontWeight = FontWeight.Bold)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Delivery Fee", color = Color(0xFF42474E))
                        Text("UGX ${deliveryFee.toInt()}", fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total Amount", fontWeight = FontWeight.Bold, color = Color(0xFF1A1C1E))
                        Text("UGX ${totalAmount.toInt()}", fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onProceedToPayment("order_456") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
            ) {
                Text("Proceed to Payment", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
