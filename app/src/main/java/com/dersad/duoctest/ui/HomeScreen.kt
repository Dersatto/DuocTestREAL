package com.dersad.duoctest.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dersad.duoctest.R
import com.dersad.duoctest.data.Producto
import kotlinx.coroutines.delay
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController, 
    cartViewModel: CartViewModel, 
    productosViewModel: ProductosViewModel = viewModel()
) {
    val productos by productosViewModel.productos.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Producto?>(null) } // Estado para el pop-up

    val destacados = remember(productos) { productos.shuffled().take(4) }
    val listados = remember(productos) { 
        productos.filter { p -> !destacados.contains(p) }.shuffled().take(10)
    }

    val productosFiltrados = remember(destacados, searchQuery) {
        if (searchQuery.isBlank()) {
            destacados
        } else {
            destacados.filter { it.nombre.contains(searchQuery, ignoreCase = true) }
        }
    }

    val lazyListState = rememberLazyListState()
    var isUserInteracting by remember { mutableStateOf(false) }
    val categorias = listOf("Trip Hop", "Alternative", "Rock", "Pop", "Jazz")

    // --- Pop-up de Vista Rápida ---
    if (selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { selectedProduct = null },
            title = { Text(selectedProduct!!.nombre) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AsyncImage(
                        model = selectedProduct!!.imageResId,
                        contentDescription = selectedProduct!!.nombre,
                        modifier = Modifier.fillMaxWidth().height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(selectedProduct!!.descripcion)
                    Text("Stock: ${selectedProduct!!.stock}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Precio: ${formatClp(selectedProduct!!.precio)}", style = MaterialTheme.typography.titleMedium)
                }
            },
            confirmButton = {
                Button(onClick = { 
                    cartViewModel.addToCart(selectedProduct!!)
                    Toast.makeText(context, "${selectedProduct!!.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                    selectedProduct = null
                }) {
                    Text("Agregar al carrito")
                }
            },
            dismissButton = { 
                OutlinedButton(onClick = { selectedProduct = null }) {
                    Text("Cerrar")
                }
            }
        )
    }

    LaunchedEffect(key1 = listados, key2 = isUserInteracting) {
        if (listados.isNotEmpty() && !isUserInteracting) {
            lazyListState.scrollToItem(0)
            while (true) {
                delay(16)
                lazyListState.scroll { scrollBy(1.0f) }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        // --- Banner de Bienvenida ---
        Box(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.v1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix(floatArrayOf(0.7f, 0f, 0f, 0f, 0f, 0f, 0.7f, 0f, 0f, 0f, 0f, 0f, 0.7f, 0f, 0f, 0f, 0f, 0f, 1f, 0f))
                )
            )
            Text(
                text = "AMARI STORE",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        // Contenido Principal
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar en destacados...") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                singleLine = true
            )

            // --- Categorías con FilterChip ---
            Text(
                "Categorías",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                items(categorias) { categoria ->
                    FilterChip(
                        selected = false, // No se mantiene la selección en HomeScreen
                        onClick = { navController.navigate("productos/$categoria") },
                        label = { Text(categoria) },
                        shape = RoundedCornerShape(50)
                    )
                }
            }

            // Productos Destacados
            Text("Productos Destacados", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp, bottom = 18.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(540.dp),
                userScrollEnabled = false
            ) {
                items(productosFiltrados) { p ->
                    Card(
                        onClick = { selectedProduct = p },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            AsyncImage(
                                model = p.imageResId,
                                contentDescription = p.nombre,
                                modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Text(p.nombre, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            Text(text = formatClp(p.precio), style = MaterialTheme.typography.labelLarge)
                            Button(onClick = { 
                                cartViewModel.addToCart(p)
                                Toast.makeText(context, "${p.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text("Agregar al carrito")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Tal vez te interese ---
            Text("Tal vez te interese", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 16.dp))
            LazyRow(
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.pointerInput(Unit) { /* ... */ }
            ) {
                items(listados) { p ->
                    Card(
                        onClick = { selectedProduct = p },
                        modifier = Modifier.width(240.dp)
                    ) {
                        Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            AsyncImage(
                                model = p.imageResId,
                                contentDescription = p.nombre,
                                modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Text(p.nombre, style = MaterialTheme.typography.titleSmall)
                            Text(text = formatClp(p.precio), style = MaterialTheme.typography.labelLarge)
                            OutlinedButton(onClick = { 
                                cartViewModel.addToCart(p)
                                Toast.makeText(context, "${p.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
                            }, modifier = Modifier.fillMaxWidth()) { Text("Agregar al carrito") }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Listado Resumido ---
            Text("Más para Descubrir", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp))
            LazyColumn(modifier = Modifier.height(400.dp)) {
                items(productos.shuffled()) { producto ->
                    ListItem(
                        headlineContent = { Text(producto.nombre, fontWeight = FontWeight.SemiBold) },
                        supportingContent = { Text(producto.categoria) },
                        trailingContent = { Text(formatClp(producto.precio)) },
                        leadingContent = {
                            AsyncImage(
                                model = producto.imageResId,
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        },
                        modifier = Modifier.clickable { selectedProduct = producto }
                    )
                    Divider()
                }
            }
        }
    }
}