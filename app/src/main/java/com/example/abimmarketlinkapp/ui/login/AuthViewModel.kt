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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    fun login(identity: String, password: String) {
        if (identity.isBlank()) {
            _authState.value = AuthState.Error("Email or Phone Number is required")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            
            try {
                // Simulate network delay
                delay(1500)
                
                // Get stored user data
                val storedUser = dataStoreManager.user.first()
                val storedPassword = dataStoreManager.userPassword.first()

                val isDefaultMock = (identity == "0770000000" || identity == "admin@abim.com") && password == "password"
                val isRegisteredUser = storedUser != null && 
                        (storedUser.phone == identity || storedUser.email == identity) && 
                        storedPassword == password

                if (isDefaultMock || isRegisteredUser) {
                    
                    val userToLogin = storedUser ?: User(
                        id = "user_default",
                        name = "Abim Admin",
                        email = "admin@abim.com",
                        phone = "0770000000",
                        role = "Farmer",
                        memberSince = "Jan 2024",
                        isNewCustomer = false,
                        avatarUrl = null
                    )
                    
                    if (storedUser == null) {
                        dataStoreManager.saveUser(userToLogin)
                    }
                    
                    _authState.value = AuthState.Success(userToLogin)
                } else {
                    _authState.value = AuthState.Error("Invalid credentials. Try your registered details or 0770000000 / password")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("An error occurred: ${e.message}")
            }
        }
    }

    fun signUp(name: String, email: String, phone: String, password: String) {
        if (name.isBlank() || email.isBlank() || phone.isBlank() || password.length < 6) {
            _authState.value = AuthState.Error("Please fill all fields. Password min 6 chars.")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                delay(1500)
                val currentDate = SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(Date())
                val newUser = User(
                    id = "user_${System.currentTimeMillis()}",
                    name = name,
                    email = email,
                    phone = phone,
                    role = "Buyer", 
                    memberSince = currentDate,
                    isNewCustomer = true, // Tracked correctly for new sign-ups
                    avatarUrl = null
                )
                dataStoreManager.saveUser(newUser, password)
                _authState.value = AuthState.Success(newUser)
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Failed to create account: ${e.message}")
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
