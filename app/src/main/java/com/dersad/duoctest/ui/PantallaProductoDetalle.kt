package com.dersad.duoctest.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dersad.duoctest.data.Producto
import java.text.DecimalFormat

@Composable
fun PantallaProductoDetalle(
    navController: NavController,
    productId: Int, // Corregido a Int
    productosViewModel: ProductosViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    LaunchedEffect(productId) {
        productosViewModel.getProducto(productId)
    }
    val producto by productosViewModel.producto.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        producto?.let {
            ProductoDetalle(it, onAddToCart = { cartViewModel.addToCart(it) })
        } ?: CircularProgressIndicator()
    }
}

@Composable
fun ProductoDetalle(producto: Producto, onAddToCart: (Producto) -> Unit) {
    val context = LocalContext.current
    val df = DecimalFormat("#,##0.##")

    Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("ID: ${producto.id}")
        Text("Nombre: ${producto.nombre}")
        Text("Descripción: ${producto.descripcion}")
        Text("Precio: $${df.format(producto.precio)}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onAddToCart(producto)
            Toast.makeText(context, "${producto.nombre} añadido al carrito", Toast.LENGTH_SHORT).show()
        }) {
            Text("Agregar al Carrito")
        }
    }
}
