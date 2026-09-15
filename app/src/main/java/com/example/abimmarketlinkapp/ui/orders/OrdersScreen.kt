package com.example.abimmarketlinkapp.ui.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.Order
import com.example.abimmarketlinkapp.data.model.OrderStatus

import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel,
    onOrderClick: (Order) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.orders_title),
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OrderTabs(
                selectedStatus = uiState.selectedStatus,
                onStatusChange = { viewModel.onStatusChange(it) }
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.orders) { order ->
                        OrderCard(
                            order = order,
                            onClick = { onOrderClick(order) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderTabs(
    selectedStatus: OrderStatus?,
    onStatusChange: (OrderStatus?) -> Unit
) {
    TabRow(
        selectedTabIndex = when (selectedStatus) {
            OrderStatus.CONFIRMED -> 0
            OrderStatus.COMPLETED -> 1
            OrderStatus.PENDING -> 2
            else -> 0
        },
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(tabPositions[when (selectedStatus) {
                    OrderStatus.CONFIRMED -> 0
                    OrderStatus.COMPLETED -> 1
                    OrderStatus.PENDING -> 2
                    else -> 0
                }]),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        Tab(
            selected = selectedStatus == OrderStatus.CONFIRMED,
            onClick = { onStatusChange(OrderStatus.CONFIRMED) },
            text = { Text(stringResource(R.string.order_tab_active)) }
        )
        Tab(
            selected = selectedStatus == OrderStatus.COMPLETED,
            onClick = { onStatusChange(OrderStatus.COMPLETED) },
            text = { Text(stringResource(R.string.order_tab_completed)) }
        )
        Tab(
            selected = selectedStatus == OrderStatus.PENDING,
            onClick = { onStatusChange(OrderStatus.PENDING) },
            text = { Text(stringResource(R.string.order_tab_pending)) }
        )
    }
}

@Composable
fun OrderCard(
    order: Order,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                Text(
                    text = "Order #${order.id}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                OrderStatusBadge(order.status)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = order.productName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${order.quantity.toInt()} units • UGX ${order.totalPrice.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${order.buyerName} • ${order.distance}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF1F8E9), contentColor = Color(0xFF2E7D32))
            ) {
                Text(text = stringResource(R.string.view_order), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun OrderStatusBadge(status: OrderStatus) {
    val color = when (status) {
        OrderStatus.CONFIRMED -> Color(0xFFE3F2FD)
        OrderStatus.COMPLETED -> Color(0xFFE8F5E9)
        OrderStatus.PENDING -> Color(0xFFFFF9C4)
        OrderStatus.PROCESSING -> Color(0xFFF3E5F5)
        OrderStatus.CANCELLED -> Color(0xFFFFEBEE)
    }
    val textColor = when (status) {
        OrderStatus.CONFIRMED -> Color(0xFF1976D2)
        OrderStatus.COMPLETED -> Color(0xFF2E7D32)
        OrderStatus.PENDING -> Color(0xFFF57F17)
        OrderStatus.PROCESSING -> Color(0xFF7B1FA2)
        OrderStatus.CANCELLED -> Color(0xFFD32F2F)
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
