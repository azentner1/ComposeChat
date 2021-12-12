package com.example.composechat.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composechat.feature.conversation.data.dao.ConversationDao
import com.example.composechat.feature.conversation.data.dao.entity.MessageEntity

@Database(
    entities = [MessageEntity::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getConversationDao(): ConversationDao
}