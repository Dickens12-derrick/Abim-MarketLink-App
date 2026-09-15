package com.example.abimmarketlinkapp.data.model

data class Message(
    val id: String,
    val senderId: String,
    val content: String,
    val timestamp: Long,
    val isFromMe: Boolean
)

data class ChatSummary(
    val id: String,
    val otherPartyName: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int,
    val otherPartyAvatar: Int? = null
)
