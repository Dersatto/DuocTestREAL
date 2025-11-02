package com.dersad.duoctest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dersad.duoctest.ui.UsuarioViewModel



@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel) {
    val estado by viewModel.estado.collectAsState()


    Column(Modifier.padding(16.dp)) {
        Text(text = "Nombre: ${estado.nombre}")
        Text(text = "Correo: ${estado.correo}")
        Text(text = "Contraseña: ${estado.contraseña}")

    }
}

