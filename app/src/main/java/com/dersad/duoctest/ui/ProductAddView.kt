@file:OptIn(ExperimentalMaterial3Api::class)

package com.dersad.duoctest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
    var stockText by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val categorias = listOf(
        "Rock", "Alternative", "Pop", "Electronic", "Progresivo",
        "Soul", "Dream Pop", "Emo", "Trip Hop", "Indie Rock",
        "Experimental", "Shoegaze", "Rock Latino"
    )

    var nombreError by remember { mutableStateOf<String?>(null) }
    var descripcionError by remember { mutableStateOf<String?>(null) }
    var precioError by remember { mutableStateOf<String?>(null) }
    var stockError by remember { mutableStateOf<String?>(null) }
    var categoriaError by remember { mutableStateOf<String?>(null) }

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
            modifier = Modifier
                .fillMaxWidth()
        )

        OutlinedTextField(
            value = stockText,
            onValueChange = {
                stockText = it
                stockError = null
            },
            label = { Text("Stock disponible") },
            isError = stockError != null,
            supportingText = {
                stockError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            OutlinedTextField(
                value = categoria,
                onValueChange = {},
                readOnly = true,
                label = { Text("Categoría") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                isError = categoriaError != null,
                supportingText = {
                    categoriaError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categorias.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            categoria = option
                            categoriaError = null
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                var valid = true
                val precio = precioText.toDoubleOrNull()
                val stock = stockText.toIntOrNull()

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
                if (stockText.isBlank() || stock == null) {
                    stockError = "Dato inválido"
                    valid = false
                }
                if (categoria.isBlank()) {
                    categoriaError = "Este campo es obligatorio"
                    valid = false
                }

                if (valid) {
                    viewModel.agregar(nombre, descripcion, precio!!, stock!!, categoria)
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
