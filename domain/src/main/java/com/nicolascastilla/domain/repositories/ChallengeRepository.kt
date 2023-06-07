package com.nicolascastilla.domain.repositories

import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {
    fun getAllData(): Flow<List<UserChatEntity>>
    fun getAllContacts(): Flow<List<ContactsEntity>>
    fun setupChatData(data: UserChatEntity)
    fun getAllConversation(data: UserChatEntity): Flow<List<Conversation>>
    fun sendMessage(message:Conversation)
}