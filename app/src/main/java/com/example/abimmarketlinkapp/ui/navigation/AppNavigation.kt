package com.example.abimmarketlinkapp.ui.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.abimmarketlinkapp.R
import com.example.abimmarketlinkapp.ui.AppViewModelProvider
import com.example.abimmarketlinkapp.ui.MainViewModel
import com.example.abimmarketlinkapp.ui.cart.CartScreen
import com.example.abimmarketlinkapp.ui.cart.CartViewModel
import com.example.abimmarketlinkapp.ui.chat.ChatDetailScreen
import com.example.abimmarketlinkapp.ui.chat.ChatScreen
import com.example.abimmarketlinkapp.ui.chat.ChatViewModel
import com.example.abimmarketlinkapp.ui.dashboard.DashboardScreen
import com.example.abimmarketlinkapp.ui.dashboard.DashboardViewModel
import com.example.abimmarketlinkapp.ui.features.FeaturesScreen
import com.example.abimmarketlinkapp.ui.login.LoginScreen
import com.example.abimmarketlinkapp.ui.marketplace.MarketplaceScreen
import com.example.abimmarketlinkapp.ui.marketplace.MarketplaceViewModel
import com.example.abimmarketlinkapp.ui.marketplace.ProductDetailScreen
import com.example.abimmarketlinkapp.ui.onboarding.OnboardingScreen
import com.example.abimmarketlinkapp.ui.orders.OrderDetailScreen
import com.example.abimmarketlinkapp.ui.orders.OrdersScreen
import com.example.abimmarketlinkapp.ui.orders.OrdersViewModel
import com.example.abimmarketlinkapp.ui.profile.ProfileScreen
import com.example.abimmarketlinkapp.ui.profile.ProfileViewModel
import com.example.abimmarketlinkapp.ui.welcome.WelcomeScreen

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Welcome : Screen("welcome")
    object Features : Screen("features")
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object Marketplace : Screen("marketplace")
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Chat : Screen("chat")
    object ChatDetail : Screen("chat_detail/{chatId}/{chatName}") {
        fun createRoute(chatId: String, chatName: String) = "chat_detail/$chatId/$chatName"
    }
    object Orders : Screen("orders")
    object OrderDetail : Screen("order_detail/{orderId}") {
        fun createRoute(orderId: String) = "order_detail/$orderId"
    }
    object Profile : Screen("profile")
    object Cart : Screen("cart")
}

@Composable
fun AppNavigation(
    windowSize: WindowWidthSizeClass,
    mainViewModel: MainViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val navController = rememberNavController()
    val isOnboarded by mainViewModel.isOnboarded.collectAsState()
    val startDestination = if (isOnboarded) Screen.Dashboard.route else Screen.Onboarding.route

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in listOf(
        Screen.Dashboard.route,
        Screen.Marketplace.route,
        Screen.Chat.route,
        Screen.Orders.route,
        Screen.Profile.route
    )

    // Shared ViewModels to persist state across screens if needed
    val cartViewModel: CartViewModel = viewModel()

    if (windowSize == WindowWidthSizeClass.Expanded) {
        Row {
            if (showBottomBar) {
                AppNavRail(navController, currentDestination)
            }
            AppNavHost(navController, startDestination, mainViewModel, cartViewModel, Modifier.weight(1f))
        }
    } else {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    AppBottomNavigation(navController, currentDestination)
                }
            }
        ) { innerPadding ->
            AppNavHost(navController, startDestination, mainViewModel, cartViewModel, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String,
    mainViewModel: MainViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(onFinished = { navController.navigate(Screen.Welcome.route) })
        }
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onJoinAsFarmer = { /* Navigate or set user type */ },
                onJoinAsBuyer = { /* Navigate or set user type */ },
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onGetStarted = { navController.navigate(Screen.Features.route) }
            )
        }
        composable(Screen.Features.route) {
            FeaturesScreen(onContinue = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    mainViewModel.setOnboarded(true)
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onSignUpClick = { /* Navigate to sign up */ }
            )
        }
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
            DashboardScreen(
                viewModel = viewModel,
                onBrowseProduceClick = { navController.navigate(Screen.Marketplace.route) },
                onNotificationClick = { /* Navigate */ },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(Screen.Marketplace.route) {
            val viewModel: MarketplaceViewModel = viewModel(factory = AppViewModelProvider.Factory)
            MarketplaceScreen(
                viewModel = viewModel,
                onProductClick = { product -> navController.navigate(Screen.ProductDetail.createRoute(product.id)) },
                onAddToCart = { product -> cartViewModel.addProduct(product) }
            )
        }
        composable(Screen.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val viewModel: MarketplaceViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val product = viewModel.uiState.collectAsState().value.products.find { it.id == productId }
            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { p, qty -> 
                        repeat(qty) { cartViewModel.addProduct(p) }
                    },
                    onContactSeller = { p -> navController.navigate(Screen.ChatDetail.createRoute("1", p.sellerName)) }
                )
            }
        }
        composable(Screen.Chat.route) {
            val viewModel: ChatViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ChatScreen(
                viewModel = viewModel,
                onChatClick = { chat -> navController.navigate(Screen.ChatDetail.createRoute(chat.id, chat.otherPartyName)) }
            )
        }
        composable(Screen.ChatDetail.route) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId") ?: ""
            val chatName = backStackEntry.arguments?.getString("chatName") ?: ""
            val viewModel: ChatViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ChatDetailScreen(
                chatId = chatId,
                chatName = chatName,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.Orders.route) {
            val viewModel: OrdersViewModel = viewModel(factory = AppViewModelProvider.Factory)
            OrdersScreen(
                viewModel = viewModel,
                onOrderClick = { order -> navController.navigate(Screen.OrderDetail.createRoute(order.id)) }
            )
        }
        composable(Screen.OrderDetail.route) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            val viewModel: OrdersViewModel = viewModel(factory = AppViewModelProvider.Factory)
            val order = viewModel.uiState.collectAsState().value.orders.find { it.id == orderId }
            if (order != null) {
                OrderDetailScreen(
                    order = order,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
            ProfileScreen(
                viewModel = viewModel,
                onLogoutClick = {
                    mainViewModel.setOnboarded(false)
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Screen.Cart.route) {
            CartScreen(
                viewModel = cartViewModel,
                onBackClick = { navController.popBackStack() },
                onCheckoutClick = { /* Navigate */ }
            )
        }
    }
}

@Composable
fun AppBottomNavigation(navController: NavHostController, currentDestination: NavDestination?) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val items = listOf(
            Triple(Screen.Dashboard.route, stringResource(R.string.nav_home), Icons.Default.Home),
            Triple(Screen.Marketplace.route, stringResource(R.string.nav_discover), Icons.Default.GridView),
            Triple(Screen.Chat.route, stringResource(R.string.nav_chat), Icons.Default.Chat),
            Triple(Screen.Orders.route, stringResource(R.string.nav_orders), Icons.Default.Receipt),
            Triple(Screen.Profile.route, stringResource(R.string.nav_profile), Icons.Default.Person)
        )

        items.forEach { (route, label, icon) ->
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun AppNavRail(navController: NavHostController, currentDestination: NavDestination?) {
    NavigationRail(
        containerColor = Color.White,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val items = listOf(
            Triple(Screen.Dashboard.route, stringResource(R.string.nav_home), Icons.Default.Home),
            Triple(Screen.Marketplace.route, stringResource(R.string.nav_discover), Icons.Default.GridView),
            Triple(Screen.Chat.route, stringResource(R.string.nav_chat), Icons.Default.Chat),
            Triple(Screen.Orders.route, stringResource(R.string.nav_orders), Icons.Default.Receipt),
            Triple(Screen.Profile.route, stringResource(R.string.nav_profile), Icons.Default.Person)
        )

        items.forEach { (route, label, icon) ->
            NavigationRailItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = currentDestination?.hierarchy?.any { it.route == route } == true,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
