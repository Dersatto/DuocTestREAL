package com.dersad.duoctest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun PantallaCarrito(vm: CartViewModel = viewModel()) {
    val cartItems by vm.cartItems.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cartItems.values.isEmpty()) {
            Text("Tu carrito está vacío.")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(cartItems.values.toList()) { item ->
                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = item.producto.imageResId,
                                contentDescription = "Imagen de ${item.producto.nombre}",
                                modifier = Modifier.size(64.dp)
                            )
                        },
                        headlineContent = { Text(item.producto.nombre) },
                        supportingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(onClick = { vm.decreaseQuantity(item.producto) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Disminuir cantidad")
                                }
                                Text("${item.quantity}")
                                IconButton(onClick = { vm.increaseQuantity(item.producto) }) {
                                    Icon(Icons.Filled.Add, contentDescription = "Aumentar cantidad")
                                }
                            }
                        },
                        trailingContent = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = formatClp(item.producto.precio * item.quantity))
                                IconButton(onClick = { vm.removeFromCart(item.producto) }) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar del carrito")
                                }
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}