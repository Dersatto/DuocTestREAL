package com.dersad.duoctest.ui

import androidx.lifecycle.ViewModel
import com.dersad.duoctest.data.Usuario
import com.dersad.duoctest.data.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel() {

    private val _estado = MutableStateFlow(value = Usuario())
    val estado: StateFlow<Usuario> = _estado

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onContraseñaChange(valor: String) {
        _estado.update { it.copy(contraseña = valor, errores = it.errores.copy(contraseña = null)) }
    }





    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores= UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "El nombre es requerido" else null,
            correo = if (estadoActual.correo.isBlank()) "El correo es requerido" else null,
            contraseña = if (estadoActual.contraseña.isBlank()) "La contraseña es requerida" else null
        )


        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.contraseña
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores


    }
}
