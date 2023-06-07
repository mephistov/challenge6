package com.nicolascastilla.domain.repositories.usecases.interfaces

import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import kotlinx.coroutines.flow.Flow

interface GetChatUseCases {
    fun setupDataBase(data: UserChatEntity)
    suspend fun sendMessage(message: Conversation)
    fun getAllConversation(data: UserChatEntity): Flow<List<Conversation>>
}