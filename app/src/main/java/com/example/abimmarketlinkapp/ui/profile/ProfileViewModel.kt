package com.example.abimmarketlinkapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import com.example.abimmarketlinkapp.data.model.User
import com.example.abimmarketlinkapp.data.model.UserType
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false
)

class ProfileViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = dataStoreManager.userType.map { type ->
        val userType = if (type == "FARMER") UserType.FARMER else UserType.BUYER
        ProfileUiState(
            user = User(
                id = "u1",
                name = "Okello Dickens",
                email = "okello@example.com",
                phoneNumber = "+256 700 000000",
                userType = userType,
                memberSince = "Oct 2023"
            )
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUiState(isLoading = true))
}
