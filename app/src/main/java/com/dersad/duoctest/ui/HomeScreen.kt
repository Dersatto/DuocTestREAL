package com.dersad.duoctest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.dersad.duoctest.R

@Composable
fun HomeScreen(navController: NavController, vm: ProductosViewModel = viewModel()) {
    val productos by vm.productos.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Poner con Slide más Productos", style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))

        if (productos.isEmpty()) {
            CircularProgressIndicator()
        } else {
            // Corregido para buscar el nombre "Shaw"
            val shawProduct = productos.find { it.nombre == "Shaw" }

            if (shawProduct != null) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("products/${shawProduct.id}") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    //Toda esta columna es el Shaw en el centro con su imagen, si queremos poner más hay q repetir, no se ponerlo en slide...
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = painterResource(id = R.mipmap.shaw_foreground),
                            contentDescription = shawProduct.nombre,
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = shawProduct.nombre,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Text(
                            text = shawProduct.descripcion,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Text("Producto no disponible. Prueba reinstalando la app.")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate("products") }) {
            Text("Ver Catálogo Completo")
        }
    }
}