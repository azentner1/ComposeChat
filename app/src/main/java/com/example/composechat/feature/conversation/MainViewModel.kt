package com.example.composechat.feature.conversation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.core.ext.timeFromTimestamp
import com.example.composechat.feature.conversation.model.Message
import com.example.composechat.feature.conversation.repository.ConversationRepository
import com.example.composechat.mock.MockMessageGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val conversationRepository: ConversationRepository
): ViewModel() {

    var messageList: List<Message> by mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            conversationRepository.getAllMessages().collect {
                when {
                    it.isEmpty() -> {
                        generateInitialConversationData()
                    }
                    else -> {
                        messageList = it
                    }
                }

            }
        }
    }

    fun addMessage(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            conversationRepository.addMessage(text)
            delay(3000)
            conversationRepository.addMessage(MockMessageGenerator.randomReplyMessage.text, isMe = false)
        }
    }

    private fun addMessages(messages: List<Message>) {
        viewModelScope.launch(Dispatchers.IO) {
            conversationRepository.addMessages(messages)
        }
    }

    private fun generateInitialConversationData() = addMessages(MockMessageGenerator.conversationSample)

    fun checkHasTail(index: Int, messages: List<Message>): Boolean {
        return when {
            index == messages.indices.last -> {
                true
            }
            messages[index].isMe != messages[index + 1].isMe -> {
                true
            }
            index != 0 && (messages[index].timestamp - messages[index - 1].timestamp < TIMESTAMP_DIFF_THRESHOLD_TAIL) -> {
                true
            }
            else -> {
                false
            }
        }
    }

    fun showDateHeader(index: Int, messages: List<Message>): Boolean {
        return when {
            messages.isEmpty() -> {
                false
            }
            messages.size == 1 -> {
                true
            }
            messages.size > 1 && index == 0 -> {
                true
            }
            index != 0 && messages[index].timestamp - messages[index - 1].timestamp > TIMESTAMP_DIFF_THRESHOLD_HEADER -> {
                true
            }
            else -> {
                false
            }
        }
    }

    companion object {
        private const val TIMESTAMP_DIFF_THRESHOLD_HEADER = 1000 * 60 * 60
        private const val TIMESTAMP_DIFF_THRESHOLD_TAIL = 20 * 1000
    }

}