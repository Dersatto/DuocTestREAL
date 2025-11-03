package com.dersad.duoctest.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
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

@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel = viewModel()) {
    val estado by viewModel.estado.collectAsStateWithLifecycle()

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

            Text(text = "Correo: ${estado.correo}")
            Text(text = "Contraseña: ${estado.contraseña}")
        }
    }
}
