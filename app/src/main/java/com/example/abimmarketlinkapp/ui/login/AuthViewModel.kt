package com.example.abimmarketlinkapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import com.example.abimmarketlinkapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: User) : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun login(phone: String, password: String) {
        if (phone.length != 10) {
            _authState.value = AuthState.Error("Phone number must be 10 digits")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            // Mock login logic
            try {
                // Simulate network delay
                delay(1500)
                
                if (phone == "0770000000" && password == "password") {
                    val mockUser = User(
                        id = "user_123",
                        name = "Abim Farmer",
                        phone = phone,
                        role = "Farmer",
                        memberSince = "Jan 2024",
                        isNewCustomer = false,
                        avatarUrl = null
                    )
                    dataStoreManager.saveUser(mockUser)
                    _authState.value = AuthState.Success(mockUser)
                } else {
                    _authState.value = AuthState.Error("Invalid credentials. Try 0770000000 / password")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearUserData()
            _authState.value = AuthState.Idle
        }
    }
}
