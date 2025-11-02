package com.dersad.duoctest.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema de colores para el tema claro (Vinilos)
private val LightColorScheme = lightColorScheme(
    primary = VinylGray,       // Color principal para botones y elementos interactivos
    secondary = MintAccent,      // Color secundario para acentos
    tertiary = MintAccent,       // Color terciario (puede ser el mismo que el secundario)
    background = OffWhite,       // Color de fondo de las pantallas
    surface = CardBackground,    // Color de fondo de las tarjetas y superficies elevadas
    onPrimary = Color.White,     // Color del texto sobre el color primario (ej. en botones)
    onSecondary = VinylGray,       // Color del texto sobre el color secundario
    onTertiary = VinylGray,
    onBackground = VinylGray,    // Color del texto sobre el color de fondo
    onSurface = VinylGray,       // Color del texto sobre las superficies
)

// Esquema de colores para el tema oscuro (Vinilos)
private val DarkColorScheme = darkColorScheme(
    primary = MintAccent,        // En modo oscuro, el menta resalta más como primario
    secondary = VinylGray,       // El gris oscuro como secundario
    tertiary = VinylGray,
    background = DarkVinylGray,  // Fondo de pantalla oscuro
    surface = DarkOffWhite,      // Fondo de tarjetas oscuro
    onPrimary = VinylGray,       // Texto oscuro sobre el primario menta
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,    // Texto blanco sobre fondo oscuro
    onSurface = Color.White,       // Texto blanco sobre superficies oscuras
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
