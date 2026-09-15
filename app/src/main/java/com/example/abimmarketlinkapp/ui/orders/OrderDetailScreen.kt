package com.example.abimmarketlinkapp.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.Order

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    order: Order,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Order Details", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OrderHeader(order)
            Spacer(modifier = Modifier.height(24.dp))
            DeliveryInfoSection(order)
            Spacer(modifier = Modifier.height(24.dp))
            OrderItemsSection(order)
            Spacer(modifier = Modifier.height(24.dp))
            PaymentSummarySection(order)
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { /* Contact Support */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Contact Support")
            }
        }
    }
}

@Composable
fun OrderHeader(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Order ID", color = Color.Gray, fontSize = 12.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = order.id, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color.Gray)
                    }
                }
                OrderStatusBadge(order.status)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Placed on ${order.date}", color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun DeliveryInfoSection(order: Order) {
    Column {
        Text(text = "Delivery Information", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Buyer: ${order.buyerName}", fontWeight = FontWeight.Bold)
                Text(text = "Location: Abim Town Market", color = Color.Gray)
                Text(text = "Distance: ${order.distance}", color = Color.Gray)
            }
        }
    }
}

@Composable
fun OrderItemsSection(order: Order) {
    Column {
        Text(text = "Items", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Placeholder for product image
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFF1F1F1)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(text = "📦")
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = order.productName, fontWeight = FontWeight.Bold)
                    Text(text = "Qty: ${order.quantity.toInt()} units", color = Color.Gray)
                }
                Text(text = "UGX ${order.totalPrice.toInt()}", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun PaymentSummarySection(order: Order) {
    Column {
        Text(text = "Payment Summary", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SummaryRow("Subtotal", "UGX ${order.totalPrice.toInt()}")
                SummaryRow("Delivery Fee", "UGX 2,000")
                Divider(modifier = Modifier.padding(vertical = 12.dp), color = Color.LightGray.copy(alpha = 0.5f))
                SummaryRow("Total", "UGX ${order.totalPrice.toInt() + 2000}", isBold = true)
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = if (isBold) Color.Black else Color.Gray, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(text = value, color = if (isBold) MaterialTheme.colorScheme.primary else Color.Black, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}
