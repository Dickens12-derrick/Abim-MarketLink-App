package com.example.abimmarketlinkapp.ui.cart

import androidx.lifecycle.ViewModel
import com.example.abimmarketlinkapp.data.model.CartItem
import com.example.abimmarketlinkapp.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CartUiState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0
)

class CartViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CartUiState())
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    fun addProduct(product: Product) {
        _uiState.update { current ->
            val existing = current.items.find { it.product.id == product.id }
            val newItems = if (existing != null) {
                current.items.map { 
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it 
                }
            } else {
                current.items + CartItem(product, 1)
            }
            current.copy(
                items = newItems,
                totalPrice = calculateTotal(newItems)
            )
        }
    }

    fun removeProduct(productId: String) {
        _uiState.update { current ->
            val newItems = current.items.filter { it.product.id != productId }
            current.copy(
                items = newItems,
                totalPrice = calculateTotal(newItems)
            )
        }
    }

    fun updateQuantity(productId: String, delta: Int) {
        _uiState.update { current ->
            val newItems = current.items.map { item ->
                if (item.product.id == productId) {
                    val newQty = (item.quantity + delta).coerceAtLeast(1)
                    item.copy(quantity = newQty)
                } else item
            }
            current.copy(
                items = newItems,
                totalPrice = calculateTotal(newItems)
            )
        }
    }

    private fun calculateTotal(items: List<CartItem>): Double {
        return items.sumOf { it.product.price * it.quantity }
    }
}
