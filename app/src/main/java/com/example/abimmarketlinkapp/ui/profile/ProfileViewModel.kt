package com.example.abimmarketlinkapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import com.example.abimmarketlinkapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    val uiState: StateFlow<ProfileUiState> = dataStoreManager.user.map { user ->
        ProfileUiState(user = user)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProfileUiState(isLoading = true))
}
