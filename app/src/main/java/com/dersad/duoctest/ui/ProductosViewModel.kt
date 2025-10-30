package com.dersad.duoctest.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dersad.duoctest.data.AppDatabase
import com.dersad.duoctest.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductosViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getInstance(application).productoDao()

    val productos = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _producto = MutableStateFlow<Producto?>(null)
    val producto = _producto.asStateFlow()

    fun getProducto(id: Int) { // Corregido a Int
        viewModelScope.launch {
            dao.getById(id).collect { 
                _producto.value = it
            }
        }
    }

    fun agregar(nombre: String, descripcion: String, precio: Double) {
        viewModelScope.launch {
            dao.upsert(
                Producto(
                    nombre = nombre.trim(),
                    descripcion = descripcion.trim(),
                    precio = precio
                )
            )
        }
    }

    fun limpiar() {
        viewModelScope.launch { dao.clear() }
    }
}