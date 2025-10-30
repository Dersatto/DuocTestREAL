package com.dersad.duoctest.ui

import androidx.lifecycle.ViewModel
import com.dersad.duoctest.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartItem(val producto: Producto, val quantity: Int)

class CartViewModel : ViewModel() {
    // El ID del producto es Int, el mapa debe usar Int como clave.
    private val _cartItems = MutableStateFlow<Map<Int, CartItem>>(emptyMap())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(producto: Producto) {
        _cartItems.value = _cartItems.value.toMutableMap().apply {
            val existingItem = this[producto.id]
            if (existingItem != null) {
                this[producto.id] = existingItem.copy(quantity = existingItem.quantity + 1)
            } else {
                this[producto.id] = CartItem(producto, 1)
            }
        }
    }

    fun removeFromCart(producto: Producto) {
        _cartItems.value = _cartItems.value.toMutableMap().apply {
            remove(producto.id)
        }
    }
}