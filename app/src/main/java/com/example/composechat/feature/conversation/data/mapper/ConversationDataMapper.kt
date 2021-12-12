package com.example.composechat.feature.conversation.data.mapper

import com.example.composechat.feature.conversation.data.dao.entity.MessageEntity
import com.example.composechat.core.mapper.EntityMapper
import com.example.composechat.feature.conversation.model.Message
import javax.inject.Inject

class ConversationDataMapper @Inject constructor() : EntityMapper<MessageEntity, Message> {
    override fun mapToEntity(domainModel: Message): MessageEntity {
        return MessageEntity(
            id = domainModel.id,
            username = domainModel.username,
            text = domainModel.text,
            isMe = domainModel.isMe,
            isSent = domainModel.isSent,
            timestamp = domainModel.timestamp
        )
    }

    override fun mapFromEntity(entity: MessageEntity): Message {
        return Message(
            id = entity.id,
            username = entity.username,
            text = entity.text,
            isMe = entity.isMe,
            isSent = entity.isSent,
            timestamp = entity.timestamp
        )  
    }
}