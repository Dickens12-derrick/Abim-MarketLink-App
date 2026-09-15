package com.example.abimmarketlinkapp.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abimmarketlinkapp.data.model.ChatSummary
import com.example.abimmarketlinkapp.data.model.Message
import com.example.abimmarketlinkapp.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

import kotlinx.coroutines.flow.map

data class ChatUiState(
    val summaries: List<ChatSummary> = emptyList(),
    val isLoading: Boolean = false
)

data class ChatDetailUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false
)

class ChatViewModel(private val chatRepository: ChatRepository) : ViewModel() {

    val chatUiState: StateFlow<ChatUiState> = chatRepository.getChatSummaries()
        .map { ChatUiState(summaries = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ChatUiState(isLoading = true))

    private val _chatDetailUiState = MutableStateFlow(ChatDetailUiState())
    val chatDetailUiState = _chatDetailUiState.asStateFlow()

    fun loadMessages(chatId: String) {
        viewModelScope.launch {
            chatRepository.getMessages(chatId).collect { messages ->
                _chatDetailUiState.value = ChatDetailUiState(messages = messages)
            }
        }
    }

    fun sendMessage(chatId: String, content: String) {
        viewModelScope.launch {
            chatRepository.sendMessage(chatId, content)
        }
    }
}
