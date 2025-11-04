package com.dersad.duoctest.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dersad.duoctest.data.ProductoComprado
import com.dersad.duoctest.data.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = com.dersad.duoctest.data.AppDatabase.getInstance(application).usuarioDao()

    // MAS FACIL
    private val _estado = MutableStateFlow(Usuario(correo = "test@example.com", contraseña = "123456"))
    val estado: StateFlow<Usuario> = _estado

    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onContraseñaChange(valor: String) {
        _estado.update { it.copy(contraseña = valor, errores = it.errores.copy(contraseña = null)) }
    }

    fun agregarCompra(items: List<CartItem>) {
        _usuarioLogueado.update { usuarioActual ->
            val nuevosProductos = items.map { ProductoComprado(it.producto, it.quantity) }
            usuarioActual?.copy(productosComprados = usuarioActual.productosComprados + nuevosProductos)
        }
    }

    fun validarFormulario(onResult: (Boolean) -> Unit) {
        val estadoActual = _estado.value

        val errores = com.dersad.duoctest.data.UsuarioErrores(
            correo = if (estadoActual.correo.isBlank()) "El correo es requerido" else null,
            contraseña = if (estadoActual.contraseña.isBlank()) "La contraseña es requerida" else null
        )
        _estado.update { it.copy(errores = errores) }

        if (errores.correo != null || errores.contraseña != null) {
            onResult(false)
            return
        }

        viewModelScope.launch {
            val usuarios = dao.getAllUsersOnce()
            val user = usuarios.find { it.correo == estadoActual.correo && it.contraseña == estadoActual.contraseña }

            if (user != null) {
                _usuarioLogueado.value = user
                onResult(true)
            } else {
                _estado.update {
                    it.copy(
                        errores = it.errores.copy(
                            correo = "Correo o contraseña incorrectos",
                            contraseña = "Correo o contraseña incorrectos"
                        )
                    )
                }
                onResult(false)
            }
        }
    }
}