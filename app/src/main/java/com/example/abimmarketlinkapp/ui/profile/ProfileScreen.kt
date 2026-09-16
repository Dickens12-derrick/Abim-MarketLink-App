package com.example.abimmarketlinkapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.User
import com.example.abimmarketlinkapp.ui.login.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Log Out") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    authViewModel.logout()
                    onLogoutClick()
                }) {
                    Text("Log Out", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Color(0xFFF8F8F8))
            ) {
                item {
                    ProfileHeader(uiState.user)
                }
                
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column {
                            ProfileMenuItem(Icons.Default.Receipt, "My Orders") { onMenuItemClick("my_orders") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.LocationOn, "Delivery Addresses") { onMenuItemClick("delivery_addresses") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.Payment, "Payment Methods") { onMenuItemClick("payment_methods") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.Favorite, "Favorites") { onMenuItemClick("favorites") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.Notifications, "Notifications") { onMenuItemClick("notifications") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.Help, "Help & Support") { onMenuItemClick("help_support") }
                            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
                            ProfileMenuItem(Icons.Default.Info, "About Abim MarketLink") { onMenuItemClick("about") }
                        }
                    }
                }
                
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color(0xFFD32F2F))
                    ) {
                        Text(text = "Log Out", fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(user: User?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1B5E20))
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = user?.name ?: "Abim Farmer",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Surface(
                modifier = Modifier.padding(top = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.White.copy(alpha = 0.2f)
            ) {
                Text(
                    text = if (user?.isNewCustomer == true) "New Customer" else "Gold Member",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user?.phone ?: "0770000000",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            Text(
                text = "Member since ${user?.memberSince ?: "2024"}",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE8F5E9)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Color(0xFF1B5E20), modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, modifier = Modifier.weight(1f), fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
    }
}
