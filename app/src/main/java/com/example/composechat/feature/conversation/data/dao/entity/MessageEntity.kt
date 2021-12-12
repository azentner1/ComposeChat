package com.example.composechat.feature.conversation.data.dao.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "is_me") val isMe: Boolean = false,
    @ColumnInfo(name = "is_sent") val isSent: Boolean = false,
    @ColumnInfo(name = "timestamp") val timestamp: Long
)