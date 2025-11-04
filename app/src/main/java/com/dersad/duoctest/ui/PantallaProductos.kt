package com.dersad.duoctest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dersad.duoctest.data.Producto
import androidx.compose.foundation.horizontalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(
    navController: NavController,
    productosViewModel: ProductosViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    initialCategory: String? = null
) {
    val productos by productosViewModel.productos.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var query by remember { mutableStateOf("") }
    var selectedProduct by remember { mutableStateOf<Producto?>(null) }
    var sortByPriceDesc by remember { mutableStateOf(false) }

    val categorias = listOf(
        "Todos", "Rock", "Alternative", "Pop", "Electronic",
        "Progresivo", "Soul", "Dream Pop", "Emo", "Trip Hop",
        "Indie Rock", "Experimental", "Shoegaze", "Rock Latino"
    )
    var selectedCategory by remember { mutableStateOf(initialCategory ?: "Todos") }


    val productosFiltrados = remember(productos, query, selectedCategory, sortByPriceDesc) {
        val filteredList = productos.filter { p ->
            (selectedCategory == "Todos" || p.categoria.equals(selectedCategory, ignoreCase = true)) &&
                    (query.isBlank() ||
                            p.nombre.contains(query, ignoreCase = true) ||
                            p.descripcion.contains(query, ignoreCase = true))
        }
        if (sortByPriceDesc) {
            filteredList.sortedByDescending { it.precio }
        } else {
            filteredList
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
                        modifier = Modifier.fillMaxWidth().height(180.dp),
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


    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar vinilos...") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { sortByPriceDesc = !sortByPriceDesc }) {
                Text(if (sortByPriceDesc) "Ordenado de Mayor a Menor" else "Ordenar de más caro a más barato")
            }
        }

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()).padding(bottom = 16.dp)
        ) {
            categorias.forEach { cat ->
                FilterChip(
                    modifier = Modifier.padding(end = 8.dp),
                    selected = cat == selectedCategory,
                    onClick = { selectedCategory = cat },
                    label = { Text(cat) },
                    shape = RoundedCornerShape(50),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
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
                            modifier = Modifier.fillMaxWidth().height(120.dp),
                            contentScale = ContentScale.Crop
                        )
                        Text(p.nombre, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Text(text = formatClp(p.precio), style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}