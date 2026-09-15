package com.example.abimmarketlinkapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val USER_TYPE = stringPreferencesKey("user_type")
        val PHONE_NUMBER = stringPreferencesKey("phone_number")
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_ONBOARDED] ?: false
    }

    suspend fun setOnboarded(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ONBOARDED] = value
        }
    }

    val themeMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[THEME_MODE] ?: "SYSTEM"
    }

    suspend fun setThemeMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode
        }
    }

    val userType: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_TYPE] ?: "BUYER"
    }

    suspend fun setUserType(type: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_TYPE] = type
        }
    }
}
