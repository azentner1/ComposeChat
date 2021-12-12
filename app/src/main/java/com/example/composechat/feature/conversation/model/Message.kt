package com.example.composechat.feature.conversation.model

data class Message(val id: String = "", val username: String = "", val text: String, val isMe: Boolean = false, val isSent: Boolean = false, val timestamp: Long = -1)