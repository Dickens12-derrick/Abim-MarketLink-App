package com.example.abimmarketlinkapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    val isOnboarded: StateFlow<Boolean> = dataStoreManager.isOnboarded
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setOnboarded(value: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setOnboarded(value)
        }
    }
}
