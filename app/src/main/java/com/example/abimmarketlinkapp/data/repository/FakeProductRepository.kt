package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeProductRepository : ProductRepository {

    private val mockProducts = listOf(
        Product(
            id = "1",
            name = "Ripe Avocados",
            price = 4500.0,
            unit = "kg",
            imageRes = R.drawable.ready_avocado,
            farmName = "Abim Greens Farm",
            sellerName = "John Okello",
            rating = 4.8f,
            reviewCount = 120,
            distance = "1.2km away",
            category = Category.FRUITS,
            isOrganic = true,
            description = "Freshly harvested Hass avocados from the heart of Abim. Perfectly ripe and ready for your table.",
            availableQuantity = 50.0
        ),
        Product(
            id = "2",
            name = "Organic Sukuma Wiki",
            price = 3000.0,
            unit = "bunch",
            imageRes = R.drawable.sukumawiki,
            farmName = "Valley View Gardens",
            sellerName = "Mary Akot",
            rating = 4.5f,
            reviewCount = 85,
            distance = "0.8km away",
            category = Category.VEGETABLES,
            isOrganic = true,
            description = "Nutrient-rich kale (Sukuma Wiki) grown without any synthetic pesticides. Fresh and crunchy.",
            availableQuantity = 100.0
        ),
        Product(
            id = "3",
            name = "Fresh Tomatoes",
            price = 3500.0,
            unit = "kg",
            imageRes = R.drawable.tomato,
            farmName = "Sunshine Farms",
            sellerName = "David Ocen",
            rating = 4.2f,
            reviewCount = 200,
            distance = "2.5km away",
            category = Category.VEGETABLES,
            isOrganic = false,
            description = "Plump and juicy red tomatoes, perfect for salads and stews.",
            availableQuantity = 150.0
        ),
        Product(
            id = "4",
            name = "Fresh Tilapia",
            price = 12000.0,
            unit = "kg",
            imageRes = R.drawable.tilapiafish,
            farmName = "Lake Kyoga Catch",
            sellerName = "Peter Anywar",
            rating = 4.9f,
            reviewCount = 50,
            distance = "5.0km away",
            category = Category.FISH,
            isOrganic = true,
            description = "Freshly caught Tilapia from Lake Kyoga. High quality and cleaned for your convenience.",
            availableQuantity = 20.0
        ),
        Product(
            id = "5",
            name = "Local Chicken",
            price = 25000.0,
            unit = "each",
            imageRes = R.drawable.local_hens,
            farmName = "Abim Poultry Haven",
            sellerName = "Sarah Auma",
            rating = 4.7f,
            reviewCount = 30,
            distance = "3.2km away",
            category = Category.POULTRY,
            isOrganic = true,
            description = "Healthy, free-range local chickens raised with care in Abim.",
            availableQuantity = 15.0
        ),
        Product(
            id = "6",
            name = "Yellow Maize",
            price = 2800.0,
            unit = "kg",
            imageRes = R.drawable.yellowmaize,
            farmName = "Highland Grains",
            sellerName = "Joseph Olum",
            rating = 4.3f,
            reviewCount = 150,
            distance = "4.1km away",
            category = Category.GRAINS,
            isOrganic = false,
            description = "High-quality yellow maize, ideal for milling or animal feed.",
            availableQuantity = 500.0
        ),
        Product(
            id = "7",
            name = "Dried Beans",
            price = 4000.0,
            unit = "kg",
            imageRes = R.drawable.dry_beans,
            farmName = "Central Abim Farms",
            sellerName = "Grace Amito",
            rating = 4.6f,
            reviewCount = 90,
            distance = "1.5km away",
            category = Category.GRAINS,
            isOrganic = true,
            description = "Superior quality dried beans, protein-packed and easy to cook.",
            availableQuantity = 200.0
        ),
        Product(
            id = "8",
            name = "Sweet Bananas",
            price = 3500.0,
            unit = "bunch",
            imageRes = R.drawable.banana,
            farmName = "Tropical Orchards",
            sellerName = "Alice Lanyero",
            rating = 4.8f,
            reviewCount = 110,
            distance = "2.0km away",
            category = Category.FRUITS,
            isOrganic = true,
            description = "Deliciously sweet local bananas, perfect for snacks and desserts.",
            availableQuantity = 40.0
        )
    )

    override fun getAllProducts(): Flow<List<Product>> = flowOf(mockProducts)

    override fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return flowOf(mockProducts.filter { it.category == category })
    }

    override fun getProductById(id: String): Flow<Product?> {
        return flowOf(mockProducts.find { it.id == id })
    }

    override fun searchProducts(query: String): Flow<List<Product>> {
        return flowOf(mockProducts.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        })
    }
}
