package com.dersad.duoctest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.TopAppBarDefaults
import com.dersad.duoctest.ui.AboutView
import com.dersad.duoctest.ui.CartViewModel
import com.dersad.duoctest.ui.HomeScreen
import com.dersad.duoctest.ui.LoginScreen
import com.dersad.duoctest.ui.PantallaCarrito
import com.dersad.duoctest.ui.PantallaProductoDetalle
import com.dersad.duoctest.ui.PantallaProductos
import com.dersad.duoctest.ui.theme.MyApplicationTheme
import com.dersad.duoctest.ui.UsuarioViewModel
import com.dersad.duoctest.ui.ProductAddView
import com.dersad.duoctest.ui.UsuarioScreen
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color
data class NavItem(val route: String, val label: String, val icon: ImageVector)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                AppEcommerce()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEcommerce() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val cartViewModel: CartViewModel = viewModel()
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()

    val navItems = listOf(
        NavItem("home", "Bienvenido", Icons.Default.Home),
        NavItem("products", "Productos", Icons.AutoMirrored.Filled.List),
        NavItem("cart", "Carrito", Icons.Filled.ShoppingCart),
        NavItem("user", "Mi Perfil", Icons.Default.AccountCircle),
        NavItem("addproduct", "Agregar Producto", Icons.Default.Add),
        NavItem("about", "Sobre Nosotros", Icons.Default.Info)
    )

    val bottomNavItems = listOf(
        NavItem("home", "Inicio", Icons.Default.Home),
        NavItem("products", "Productos", Icons.AutoMirrored.Filled.List),
        NavItem("cart", "Carrito", Icons.Filled.ShoppingCart),
        NavItem("user", "Perfil", Icons.Default.AccountCircle)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBars = currentRoute != "login"

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = showBars,
        drawerContent = {
            ModalDrawerSheet {
                navItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (showBars) {
                    TopAppBar(
                        title = { Text(navItems.find { it.route == currentRoute }?.label ?: "Mi Tienda") },
                        navigationIcon = {
                            if (navController.previousBackStackEntry != null) {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
                                }
                            } else {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(Icons.Default.Menu, "Menú")
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFF222222),
                            titleContentColor = Color.White)
                    )
                }
            },

            bottomBar = {
                if (showBars) {
                    NavigationBar {
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                icon = {
                                    if (item.route == "cart") {
                                        BadgedBox(badge = {
                                            if (cartItems.isNotEmpty()) {
                                                Badge { Text("${cartItems.size}") }
                                            }
                                        }) {
                                            Icon(item.icon, contentDescription = item.label)
                                        }
                                    } else {
                                        Icon(item.icon, contentDescription = item.label)
                                    }
                                },
                                label = { Text(item.label) },
                                selected = currentRoute == item.route,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }

        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") { LoginScreen(navController, usuarioViewModel) }
                composable("home") { HomeScreen(navController = navController, cartViewModel = cartViewModel) }
                composable("products") { PantallaProductos(navController, cartViewModel = cartViewModel) }
                composable("cart") { PantallaCarrito(vm = cartViewModel, usuarioViewModel = usuarioViewModel) }
                composable("user") { UsuarioScreen(usuarioViewModel) }
                composable("addproduct") { ProductAddView(navController) }
                composable("about") { AboutView(navController) }
                composable("productos/{categoria}") { backStackEntry ->
                    val categoria = backStackEntry.arguments?.getString("categoria")
                    PantallaProductos(navController, initialCategory = categoria)
                }


                composable(
                    "products/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")
                    requireNotNull(productId) { "El id del producto no puede ser nulo" }
                    PantallaProductoDetalle(
                        navController = navController,
                        productId = productId,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}