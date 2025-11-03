package com.dersad.duoctest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController



@Composable
fun ProductAddView(
    navController: NavController,
    viewModel: ProductosViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }

    var nombreError by remember { mutableStateOf<String?>(null) }
    var descripcionError by remember { mutableStateOf<String?>(null) }
    var precioError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Ingrese los datos de su producto",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 14.dp),
            textAlign = TextAlign.Center
            )

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                nombreError = null
            },
            label = { Text("Nombre del producto") },
            isError = nombreError != null,
            supportingText = {
                nombreError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
                descripcionError = null
            },
            label = { Text("Descripción") },
            isError = descripcionError != null,
            supportingText = {
                descripcionError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precioText,
            onValueChange = {
                precioText = it
                precioError = null
            },
            label = { Text("Precio") },
            isError = precioError != null,
            supportingText = {
                precioError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            shape = RoundedCornerShape(20.dp),

            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        )

        Button(
            onClick = {
                var valid = true
                val precio = precioText.toDoubleOrNull()

                if (nombre.isBlank()) {
                    nombreError = "Este campo es obligatorio"
                    valid = false
                }
                if (descripcion.isBlank()) {
                    descripcionError = "Este campo es obligatorio"
                    valid = false
                }
                if (precioText.isBlank() || precio == null) {
                    precioError = "Este campo es obligatorio"
                    valid = false
                }

                if (valid) {
                    viewModel.agregar(nombre, descripcion, precio!!)
                    navController.popBackStack()
                }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Guardar Producto")
        }
    }
}
