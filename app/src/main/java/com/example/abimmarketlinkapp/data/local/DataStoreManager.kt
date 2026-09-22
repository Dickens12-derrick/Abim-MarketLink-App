package com.example.abimmarketlinkapp.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.abimmarketlinkapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        
        // User Profile Keys
        val USER_ID = stringPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val USER_PHONE = stringPreferencesKey("user_phone")
        val USER_ROLE = stringPreferencesKey("user_role")
        val MEMBER_SINCE = stringPreferencesKey("member_since")
        val IS_NEW_CUSTOMER = booleanPreferencesKey("is_new_customer")
        val AVATAR_URL = stringPreferencesKey("avatar_url")
        val USER_PASSWORD = stringPreferencesKey("user_password") // Added for mock auth
    }

    val isOnboarded: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_ONBOARDED] ?: false
    }

    suspend fun setOnboarded(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ONBOARDED] = value
        }
    }

    val user: Flow<User?> = context.dataStore.data.map { preferences ->
        val id = preferences[USER_ID] ?: return@map null
        User(
            id = id,
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL],
            phone = preferences[USER_PHONE] ?: "",
            role = preferences[USER_ROLE] ?: "BUYER",
            memberSince = preferences[MEMBER_SINCE] ?: "",
            isNewCustomer = preferences[IS_NEW_CUSTOMER] ?: true,
            avatarUrl = preferences[AVATAR_URL]
        )
    }

    val userPassword: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_PASSWORD]
    }

    suspend fun saveUser(user: User, password: String? = null) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID] = user.id
            preferences[USER_NAME] = user.name
            preferences[USER_EMAIL] = user.email ?: ""
            preferences[USER_PHONE] = user.phone
            preferences[USER_ROLE] = user.role
            preferences[MEMBER_SINCE] = user.memberSince
            preferences[IS_NEW_CUSTOMER] = user.isNewCustomer
            user.avatarUrl?.let { preferences[AVATAR_URL] = it }
            password?.let { preferences[USER_PASSWORD] = it }
        }
    }

    suspend fun clearUserData() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID)
            preferences.remove(USER_NAME)
            preferences.remove(USER_EMAIL)
            preferences.remove(USER_PHONE)
            preferences.remove(USER_ROLE)
            preferences.remove(MEMBER_SINCE)
            preferences.remove(IS_NEW_CUSTOMER)
            preferences.remove(AVATAR_URL)
            preferences.remove(USER_PASSWORD)
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
}
