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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.dersad.duoctest.ui.UsuarioViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: UsuarioViewModel) {

    val estado by viewModel.estado.collectAsState()


    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = { Text("Nombre") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.nombre?.let {
                    Text(text=it, color=MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()


        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
                    label = { Text("Correo") },
            isError = estado.errores.nombre != null,
            supportingText = {
                estado.errores.correo?.let {
                    Text(text=it, color=MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()


        )


        OutlinedTextField(
            value = estado.contraseña,
            onValueChange = viewModel::onContraseñaChange,
                    label = { Text("Contraseña") },
            isError = estado.errores.contraseña != null,
            supportingText = {
                estado.errores.contraseña?.let {
                    Text(text=it, color=MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()

        )



        Button(
            onClick = {
                if(viewModel.validarFormulario()) {
                    navController.navigate("user")
                }
            },
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "Iniciar Sesión")
        }





    }

}