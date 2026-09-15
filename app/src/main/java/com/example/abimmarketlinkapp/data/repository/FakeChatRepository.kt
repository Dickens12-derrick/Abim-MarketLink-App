package com.example.abimmarketlinkapp.data.repository

import com.example.abimmarketlinkapp.data.model.ChatSummary
import com.example.abimmarketlinkapp.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeChatRepository : ChatRepository {

    private val _messages = MutableStateFlow<Map<String, List<Message>>>(
        mapOf(
            "1" to listOf(
                Message("m1", "buyer1", "Can you supply 50kg tomatoes?", System.currentTimeMillis() - 3600000, false),
                Message("m2", "me", "Yes, I have them available. When do you need them?", System.currentTimeMillis() - 3000000, true)
            ),
            "2" to listOf(
                Message("m3", "buyer2", "We need 100kg of maize.", System.currentTimeMillis() - 7200000, false)
            )
        )
    )

    private val chatSummaries = listOf(
        ChatSummary("1", "Nairobi Market Ltd", "Can you supply 50kg tomatoes?", "10:30 AM", 2),
        ChatSummary("2", "Fresh Produce Buyer", "We need 100kg of maize.", "Yesterday", 0),
        ChatSummary("3", "Lakeside Cafe", "Can you deliver to town market tomorrow?", "Monday", 0)
    )

    override fun getChatSummaries(): Flow<List<ChatSummary>> = flowOf(chatSummaries)

    override fun getMessages(chatId: String): Flow<List<Message>> {
        return _messages.map { it[chatId] ?: emptyList() }
    }

    override suspend fun sendMessage(chatId: String, content: String) {
        val newMessage = Message(
            id = System.currentTimeMillis().toString(),
            senderId = "me",
            content = content,
            timestamp = System.currentTimeMillis(),
            isFromMe = true
        )
        _messages.update { current ->
            val existing = current[chatId] ?: emptyList()
            current + (chatId to (existing + newMessage))
        }
    }
}
