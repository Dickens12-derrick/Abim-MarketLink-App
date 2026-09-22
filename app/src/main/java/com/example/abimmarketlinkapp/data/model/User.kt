package com.example.abimmarketlinkapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String? = null,
    val phone: String,
    val role: String,
    val memberSince: String,
    val isNewCustomer: Boolean,
    val avatarUrl: String? = null
)
