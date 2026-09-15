package com.example.abimmarketlinkapp.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val userType: UserType,
    val memberSince: String,
    val avatarRes: Int? = null
)

enum class UserType {
    FARMER, BUYER
}
