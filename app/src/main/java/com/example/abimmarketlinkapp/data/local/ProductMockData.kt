package com.example.abimmarketlinkapp.data.local

import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.data.model.Category
import com.example.abimmarketlinkapp.data.model.Product

object ProductMockData {
    val products = listOf(
        // Fruits
        createProduct("f1", "Hass Avocado", 2500.0, "pc", R.drawable.ready_avocado, Category.FRUITS, "Abim Greens"),
        createProduct("f2", "Sweet Banana", 3000.0, "bunch", R.drawable.banana, Category.FRUITS, "Lira Orchards"),
        createProduct("f3", "Juicy Mango", 1500.0, "pc", R.drawable.mango, Category.FRUITS, "Teso Fruits"),
        createProduct("f4", "Big Pineapple", 4000.0, "pc", R.drawable.pinapple, Category.FRUITS, "Central Farms"),
        createProduct("f5", "Watermelon", 6000.0, "pc", R.drawable.watermelon, Category.FRUITS, "River Bank"),
        createProduct("f6", "Ripe Pawpaw", 2500.0, "pc", R.drawable.pawpaw, Category.FRUITS, "Garden Fresh"),
        createProduct("f7", "Passion Fruit", 5000.0, "kg", R.drawable.passion_fruit, Category.FRUITS, "Hillside"),
        createProduct("f8", "Valencial Oranges", 3500.0, "kg", R.drawable.orange, Category.FRUITS, "Sunshine"),

        // Vegetables
        createProduct("v1", "Red Tomatoes", 2000.0, "kg", R.drawable.tomato, Category.VEGETABLES, "Abim Veggies"),
        createProduct("v2", "Green Cabbage", 1500.0, "pc", R.drawable.cabbage1, Category.VEGETABLES, "Cool Highlands"),
        createProduct("v3", "Sukuma Wiki", 1000.0, "bunch", R.drawable.sukumawiki, Category.VEGETABLES, "Local Market"),
        createProduct("v4", "Sweet Carrots", 2500.0, "kg", R.drawable.carrot, Category.VEGETABLES, "Root Farm"),
        createProduct("v5", "Green Pepper", 2000.0, "kg", R.drawable.green_paper, Category.VEGETABLES, "Eco Garden"),
        createProduct("v6", "Egg Plant", 1200.0, "kg", R.drawable.egg_plant, Category.VEGETABLES, "Purple Fields"),
        createProduct("v7", "Dodo Greens", 800.0, "bunch", R.drawable.dodo, Category.VEGETABLES, "Village Farm"),

        // Grains
        createProduct("g1", "Yellow Maize", 1200.0, "kg", R.drawable.yellowmaize, Category.GRAINS, "Grain Masters"),
        createProduct("g2", "Red Beans", 3500.0, "kg", R.drawable.dry_beans, Category.GRAINS, "Legume World"),
        createProduct("g3", "Groundnuts", 5000.0, "kg", R.drawable.groundnuts, Category.GRAINS, "Nutty Farm"),
        createProduct("g4", "Finger Millet", 4000.0, "kg", R.drawable.millet, Category.GRAINS, "Heritage Grains"),
        createProduct("g5", "Sorghum", 3000.0, "kg", R.drawable.sorghum, Category.GRAINS, "Dryland Grains"),

        // Livestock
        createProduct("l1", "Dairy Cow", 1500000.0, "each", R.drawable.dairy_cow, Category.LIVESTOCK, "Milk Haven"),
        createProduct("l2", "Boer Goat", 250000.0, "each", R.drawable.goats, Category.LIVESTOCK, "Goat Hill"),
        createProduct("l3", "Local Sheep", 180000.0, "each", R.drawable.sheep, Category.LIVESTOCK, "Woolly Acres"),
        createProduct("l4", "Hybrid Pig", 300000.0, "each", R.drawable.pigs, Category.LIVESTOCK, "Pork Valley"),

        // Fish
        createProduct("fi1", "Fresh Tilapia", 12000.0, "kg", R.drawable.tilapiafish, Category.FISH, "Lakeside"),
        createProduct("fi2", "Dried Mukene", 8000.0, "tin", R.drawable.mukene, Category.FISH, "Lake Kyoga"),
        createProduct("fi3", "Nile Perch", 15000.0, "kg", R.drawable.nile_perch, Category.FISH, "Victoria Catch"),
        createProduct("fi4", "Silver Fish", 7000.0, "tin", R.drawable.silver_fish, Category.FISH, "Lake View"),

        // Poultry
        createProduct("p1", "Local Chicken", 25000.0, "each", R.drawable.local_hens, Category.POULTRY, "Village Birds"),
        createProduct("p2", "Turkey", 80000.0, "each", R.drawable.turkey, Category.POULTRY, "Festive Farm"),
        createProduct("p3", "Duck", 35000.0, "each", R.drawable.ducks, Category.POULTRY, "Quack Farm"),
        createProduct("p4", "Tray of Eggs", 12000.0, "tray", R.drawable.eggs1, Category.POULTRY, "Egg Layer"),

        // Oils
        createProduct("o1", "Sunflower Oil", 7000.0, "L", R.drawable.palm_oil, Category.OILS, "Sun Oil"),
        createProduct("o2", "Palm Oil", 6500.0, "L", R.drawable.palm_oil, Category.OILS, "Palm Grove"),
        createProduct("o3", "Simsim Oil", 9000.0, "L", R.drawable.simsim_plant_oil, Category.OILS, "Seed Oil"),

        // Dairy
        createProduct("d1", "Fresh Milk", 2000.0, "L", R.drawable.milkfresh, Category.DAIRY, "Dairy Best"),
        createProduct("d2", "Local Ghee", 15000.0, "kg", R.drawable.ghee, Category.DAIRY, "Traditional Dairy"),
        createProduct("d3", "Salted Butter", 8000.0, "500g", R.drawable.butter, Category.DAIRY, "Creamy Valley"),
        createProduct("d4", "Strawberry Yogurt", 3000.0, "500ml", R.drawable.yogut, Category.DAIRY, "Sweet Dairy"),
        createProduct("d5", "Cheddar Cheese", 12000.0, "500g", R.drawable.cheese, Category.DAIRY, "Cheese House")
    )

    private fun createProduct(
        id: String,
        name: String,
        price: Double,
        unit: String,
        imageRes: Int,
        category: Category,
        farmName: String
    ): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            unit = unit,
            imageRes = imageRes,
            farmName = farmName,
            sellerName = "$farmName Owner",
            rating = (40..50).random() / 10f,
            reviewCount = (10..500).random(),
            distance = "${(1..10).random()}km away",
            category = category,
            isOrganic = listOf(true, false).random(),
            description = "High quality $name freshly produced from $farmName. Support local farmers.",
            availableQuantity = (10..100).random().toDouble()
        )
    }
}
