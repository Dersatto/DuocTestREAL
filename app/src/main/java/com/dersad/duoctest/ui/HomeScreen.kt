package com.dersad.duoctest.ui

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel,
    productosViewModel: ProductosViewModel = viewModel()
) {
    val productos by productosViewModel.productos.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var selectedProduct by remember { mutableStateOf<Producto?>(null) }
    var selectedCategory by remember { mutableStateOf("Todos") }

    val categorias = listOf(
        "Todos", "Rock", "Alternative", "Pop", "Electronic",
        "Progresivo", "Soul", "Dream Pop", "Emo", "Trip Hop",
        "Indie Rock", "Experimental", "Shoegaze", "Rock Latino"
    )

    val filteredProducts = remember(productos, selectedCategory) {
        if (selectedCategory == "Todos") productos
        else productos.filter { it.categoria.equals(selectedCategory, ignoreCase = true) }
    }

    val destacados = remember(filteredProducts) { filteredProducts.shuffled().take(4) }
    val listados = remember(productos) { productos.filter { p -> !destacados.contains(p) }.shuffled().take(10) }

    val lazyListState = rememberLazyListState()
    var isUserInteracting by remember { mutableStateOf(false) }

    LaunchedEffect(listados, isUserInteracting) {
        if (listados.isNotEmpty() && !isUserInteracting) {
            lazyListState.scrollToItem(0)
            while (true) {
                delay(16)
                lazyListState.scrollBy(1.0f)
            }
        }
    }

    if (selectedProduct != null) {
        AlertDialog(
            onDismissRequest = { selectedProduct = null },
            title = { Text(selectedProduct!!.nombre) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AsyncImage(
                        model = selectedProduct!!.imageResId,
                        contentDescription = selectedProduct!!.nombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(selectedProduct!!.descripcion)
                    Text(
                        text = "Precio: ${formatClp(selectedProduct!!.precio)}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    cartViewModel.addToCart(selectedProduct!!)
                    Toast.makeText(
                        context,
                        "${selectedProduct!!.nombre} añadido al carrito",
                        Toast.LENGTH_SHORT
                    ).show()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.v1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix(
                        floatArrayOf(
                            0.7f, 0f, 0f, 0f, 0f,
                            0f, 0.7f, 0f, 0f, 0f,
                            0f, 0f, 0.7f, 0f, 0f,
                            0f, 0f, 0f, 1f, 0f
                        )
                    )
                )
            )
            Text(
                text = "NOMBRE DE LA TIENDA DE VINILOS",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Categorías",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                items(categorias) { cat ->
                    val isSelected = cat == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        // ✅ Navigate to ProductosScreen passing selected category
                        onClick = { navController.navigate("productos/${cat}") },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            // --- Productos Destacados ---
            Text(
                "Productos Destacados",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 8.dp, bottom = 18.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(540.dp),
                userScrollEnabled = false
            ) {
                items(destacados) { p ->
                    Card(
                        onClick = { selectedProduct = p },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = p.imageResId,
                                contentDescription = p.nombre,
                                modifier = Modifier.fillMaxWidth().height(120.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(
                                p.nombre,
                                style = MaterialTheme.typography.titleSmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = formatClp(p.precio),
                                style = MaterialTheme.typography.labelLarge
                            )
                            Button(
                                onClick = {
                                    cartViewModel.addToCart(p)
                                    Toast.makeText(
                                        context,
                                        "${p.nombre} añadido al carrito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Agregar al carrito")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Tal vez te interese",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            LazyRow(
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            isUserInteracting = event.type.toString() == "down"
                        }
                    }
                }
            ) {
                items(listados) { p ->
                    Card(onClick = { selectedProduct = p }, modifier = Modifier.width(240.dp)) {
                        Column(
                            Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AsyncImage(
                                model = p.imageResId,
                                contentDescription = p.nombre,
                                modifier = Modifier.fillMaxWidth().height(120.dp),
                                contentScale = ContentScale.Crop
                            )
                            Text(p.nombre, style = MaterialTheme.typography.titleSmall)
                            Text(text = formatClp(p.precio), style = MaterialTheme.typography.labelLarge)
                            OutlinedButton(
                                onClick = {
                                    cartViewModel.addToCart(p)
                                    Toast.makeText(
                                        context,
                                        "${p.nombre} añadido al carrito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) { Text("Agregar al carrito") }
                        }
                    }
                }
            }
        }
    }
}
