package com.dersad.duoctest.ui

import androidx.lifecycle.ViewModel
import com.dersad.duoctest.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CartItem(val producto: Producto, val quantity: Int)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<Map<Int, CartItem>>(emptyMap())
    val cartItems: StateFlow<Map<Int, CartItem>> = _cartItems.asStateFlow()

    fun addToCart(producto: Producto) {
        _cartItems.update { currentItems ->
            val newItems = currentItems.toMutableMap()
            val existingItem = newItems[producto.id]
            if (existingItem != null) {
                newItems[producto.id] = existingItem.copy(quantity = existingItem.quantity + 1)
            } else {
                newItems[producto.id] = CartItem(producto, 1)
            }
            newItems
        }
    }

    fun removeFromCart(producto: Producto) {
        _cartItems.update { currentItems ->
            currentItems - producto.id
        }
    }

    fun increaseQuantity(producto: Producto) {
        addToCart(producto) 
    }

    fun decreaseQuantity(producto: Producto) {
        _cartItems.update { currentItems ->
            val newItems = currentItems.toMutableMap()
            val existingItem = newItems[producto.id]
            if (existingItem != null) {
                if (existingItem.quantity > 1) {
                    newItems[producto.id] = existingItem.copy(quantity = existingItem.quantity - 1)
                } else {
                    newItems.remove(producto.id)
                }
            }
            newItems
        }
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }
}