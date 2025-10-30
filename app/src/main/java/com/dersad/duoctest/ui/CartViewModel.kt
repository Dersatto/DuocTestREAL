package com.dersad.duoctest.ui

import androidx.lifecycle.ViewModel
import com.dersad.duoctest.data.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartItem(val producto: Producto, val quantity: Int)

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<Map<Int, CartItem>>(emptyMap())
    val cartItems = _cartItems.asStateFlow()

    fun addToCart(producto: Producto) {

        val newCartItems = _cartItems.value.toMutableMap()
        val existingItem = newCartItems[producto.id]

        if (existingItem != null) {
            newCartItems[producto.id] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            newCartItems[producto.id] = CartItem(producto, 1)
        }

        _cartItems.value = newCartItems
    }

    fun removeFromCart(producto: Producto) {
        val newCartItems = _cartItems.value.toMutableMap()
        newCartItems.remove(producto.id)
        _cartItems.value = newCartItems
    }

    fun increaseQuantity(producto: Producto) {
        addToCart(producto) //
    }

    fun decreaseQuantity(producto: Producto) {
        val newCartItems = _cartItems.value.toMutableMap()
        val existingItem = newCartItems[producto.id]

        if (existingItem != null) {
            if (existingItem.quantity > 1) {
                newCartItems[producto.id] = existingItem.copy(quantity = existingItem.quantity - 1)
            } else {
                newCartItems.remove(producto.id)
            }
        }
        _cartItems.value = newCartItems
    }

    fun clearCart() {
        _cartItems.value = emptyMap()
    }
}