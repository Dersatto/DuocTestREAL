package com.dersad.duoctest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun PantallaCarrito(vm: CartViewModel = viewModel(), usuarioViewModel: UsuarioViewModel = viewModel()) {
    val cartItems by vm.cartItems.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val itemsList = cartItems.values.toList()
    val subtotal = itemsList.sumOf { it.producto.precio * it.quantity }
    val costoEnvio = if (subtotal > 0) 4000.0 else 0.0 // Envío solo si hay productos
    val totalGeneral = subtotal + costoEnvio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (itemsList.isEmpty()) {
            Text("Tu carrito está vacío.", textAlign = TextAlign.Center)
        } else {
            // Lista de productos en el carrito
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(itemsList) { item ->
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
                                    Icon(Icons.Filled.Delete, "Disminuir cantidad")
                                }
                                Text("${item.quantity}")
                                IconButton(onClick = { vm.increaseQuantity(item.producto) }) {
                                    Icon(Icons.Filled.Add, "Aumentar cantidad")
                                }
                            }
                        },
                        trailingContent = {
                            Text(text = formatClp(item.producto.precio * item.quantity))
                        }
                    )
                }
            }

            // Resumen de la compra
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                // Subtotal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal:", style = MaterialTheme.typography.bodyLarge)
                    Text(formatClp(subtotal), style = MaterialTheme.typography.bodyLarge)
                }

                // Envío
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Envío:", style = MaterialTheme.typography.bodyLarge)
                    Text(formatClp(costoEnvio), style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                // Total General
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(formatClp(totalGeneral), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de compra
                Button(
                    onClick = { 
                        vm.comprar(usuarioViewModel)
                        Toast.makeText(context, "Gracias por tu compra", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Comprar")
                }
            }
        }
    }
}