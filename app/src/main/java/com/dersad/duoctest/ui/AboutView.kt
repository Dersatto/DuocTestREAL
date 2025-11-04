package com.dersad.duoctest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dersad.duoctest.R

@Composable
fun AboutView(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sección Superior: Logo y Descripción
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.lgo_foreground),
                contentDescription = "Logo de la Tienda",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Sobre AMARI STORE",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Somos una tienda apasionada por la música y el formato físico. Creemos que la experiencia de escuchar un vinilo es única e irremplazable. Nuestro objetivo es ofrecer una cuidada selección de clásicos y novedades para coleccionistas y nuevos aficionados.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Divider()

        // Sección de Equipo
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            Text(
                "Integrantes del Equipo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Lista de integrantes con su imagen
            val teamMembers = listOf(
                "Sofia Mayorga" to R.mipmap.shaw_foreground,
                "Jorge Osorio" to R.mipmap.mrfrog_foreground
            )

            teamMembers.forEach { (name, imageRes) ->
                ListItem(
                    headlineContent = { Text(name, fontWeight = FontWeight.Medium) },
                    supportingContent = { Text("Programador/a y Diseñador/a") },
                    leadingContent = {
                        // Imagen del integrante
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = "Foto de $name",
                            contentScale = ContentScale.Crop, // Asegura que la imagen llene el espacio
                            modifier = Modifier
                                .size(56.dp) // Un tamaño adecuado para un avatar
                                .clip(CircleShape) // Recorta la imagen en forma de círculo
                        )
                    }
                )
                Divider()
            }
        }

        // --- Sección de Redes Sociales ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Nuestras Redes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                Icon(Icons.Default.Place, contentDescription = "Instagram", modifier = Modifier.size(40.dp))
                Icon(Icons.Default.Call, contentDescription = "Facebook", modifier = Modifier.size(40.dp))
                Icon(Icons.Default.Build, contentDescription = "Twitter", modifier = Modifier.size(40.dp))
            }
        }
    }
}