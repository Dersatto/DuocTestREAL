package com.dersad.duoctest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel = viewModel()) {
    val usuario by viewModel.usuarioLogueado.collectAsStateWithLifecycle()
    val totalCompra = usuario?.productosComprados?.sumOf { it.producto.precio * it.quantity } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .width(280.dp)
                .height(300.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFE0E0E0))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )


        {

            Text(text = "Perfil de Usuario")
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Profile Icon",
                modifier = Modifier.size(100.dp),
                tint = Color(0xFF2E2E2E)
            )

            Spacer(modifier = Modifier.height(12.dp))

            usuario?.let {
                Text(text = "Correo: ${it.correo}")
                Text(text = "Contraseña: ${it.contraseña}")
                Spacer(modifier = Modifier.height(16.dp))
                Text("Total de la compra: ${formatClp(totalCompra)}")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Productos Comprados")
        LazyColumn {
            if (usuario?.productosComprados?.isEmpty() == true) {
                item {
                    Text("No has comprado productos.")
                }
            } else {
                items(usuario?.productosComprados ?: emptyList()) { productoComprado ->
                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = productoComprado.producto.imageResId,
                                contentDescription = "Imagen de ${productoComprado.producto.nombre}",
                                modifier = Modifier.size(64.dp)
                            )
                        },
                        headlineContent = { Text(productoComprado.producto.nombre) },
                        supportingContent = { Text("Cantidad: ${productoComprado.quantity}") },
                        trailingContent = { Text(formatClp(productoComprado.producto.precio * productoComprado.quantity)) }
                    )
                }
            }
        }
    }
}
