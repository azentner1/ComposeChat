package com.example.composechat.feature.conversation.repository

import com.example.composechat.feature.conversation.data.dao.ConversationDao
import com.example.composechat.feature.conversation.data.mapper.ConversationDataMapper
import com.example.composechat.feature.conversation.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ConversationRepository @Inject constructor(
    private val conversationDao: ConversationDao,
    private val conversationDataMapper: ConversationDataMapper
) {

    fun getAllMessages(): Flow<List<Message>> {
        return conversationDao.getAllMessages().map {
            it.map {
                conversationDataMapper.mapFromEntity(it)
            }.sortedBy {
                it.timestamp
            }
        }
    }

    fun addMessage(text: String, isMe: Boolean = true) {
        conversationDao.addMessage(conversationDataMapper.mapToEntity(buildMessage(text = text, isMe = isMe)))
    }

    private fun buildMessage(text: String, isMe: Boolean): Message {
        return Message(
            id = UUID.randomUUID().toString(),
            username = "me",
            text = text,
            isMe = isMe,
            isSent = false,
            timestamp = System.currentTimeMillis()
        )
    }

    fun addMessages(messages: List<Message>) {
        conversationDao.addMessages(messages = messages.map {
            conversationDataMapper.mapToEntity(buildMessage(it.text, it.isMe))
        })
    }

}