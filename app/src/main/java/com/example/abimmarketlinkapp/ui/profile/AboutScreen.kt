package com.example.abimmarketlinkapp.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    onBack: () -> Unit,
    onContactSupport: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About Abim MarketLink", fontWeight = FontWeight.Bold, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF1B5E20))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Abim MarketLink",
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Abim MarketLink is your one-stop digital marketplace connecting local Ugandan farmers with buyers directly. We deal with high-quality agricultural products including:",
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ProductCategoryItem("Fruits", "Mangoes, Bananas, Oranges, Avocados, Pineapples, etc.")
                ProductCategoryItem("Vegetables", "Tomatoes, Cabbages, Sukuma Wiki, Carrots, Onions, etc.")
                ProductCategoryItem("Grains", "Maize, Beans, Groundnuts, Millet, Sorghum.")
                ProductCategoryItem("Livestock", "Cattle, Goats, Sheep, Pigs.")
                ProductCategoryItem("Poultry", "Local Chicken, Turkey, Ducks, Eggs.")
                ProductCategoryItem("Fish", "Tilapia, Nile Perch, Silver Fish (Mukene).")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Our mission is to cut out the middlemen and ensure farmers get the best prices while buyers receive fresh produce directly from the farm.",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onContactSupport,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20))
            ) {
                Text("Contact Help & Support", fontWeight = FontWeight.Black, fontSize = 16.sp, color = Color.White)
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ProductCategoryItem(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Black, color = Color(0xFF1B5E20), fontSize = 18.sp)
            Text(text = description, color = Color.Black, fontWeight = FontWeight.Medium)
        }
    }
}
