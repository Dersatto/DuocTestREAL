package com.dersad.duoctest.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.DecimalFormat

@Composable
fun PantallaCarrito(vm: CartViewModel = viewModel()) {
    val cartItems by vm.cartItems.collectAsStateWithLifecycle()
    val df = DecimalFormat("#,##0.##")

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
                        headlineContent = { Text(item.producto.nombre) },
                        supportingContent = { Text("Cantidad: ${item.quantity}") },
                        trailingContent = { Text("$${df.format(item.producto.precio * item.quantity)}") }
                    )
                    Divider()
                }
            }
        }
    }
}