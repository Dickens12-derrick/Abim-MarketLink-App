package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.ChatSummary
import com.example.abimmarketlinkapp.data.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getChatSummaries(): Flow<List<ChatSummary>>
    fun getMessages(chatId: String): Flow<List<Message>>
    suspend fun sendMessage(chatId: String, content: String)
}
