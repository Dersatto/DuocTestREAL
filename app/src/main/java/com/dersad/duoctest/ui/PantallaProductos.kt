package com.dersad.duoctest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dersad.duoctest.data.Producto
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(navController: NavController, vm: ProductosViewModel = viewModel()) {
    val productos by vm.productos.collectAsStateWithLifecycle()
    val df = remember { DecimalFormat("#,##0.##") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(productos) { p: Producto ->
            ListItem(
                headlineContent = { Text(p.nombre) },
                supportingContent = { Text(p.descripcion) },
                leadingContent = {
                    Image(
                        painter = painterResource(id = p.imageResId),
                        contentDescription = p.nombre,
                        modifier = Modifier.size(56.dp)
                    )
                },
                trailingContent = { Text("#${p.id}") },
                modifier = Modifier.clickable { navController.navigate("products/${p.id}") }
            )
            Divider()
        }
    }
}