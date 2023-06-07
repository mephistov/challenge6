package com.nicolascastilla.domain.repositories.usecases

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetChatUseCases
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatUseCasesImpl @Inject constructor(
    private val repository: ChallengeRepository
):GetChatUseCases {
    override fun setupDataBase(data: UserChatEntity) {
        repository.setupChatData(data)
    }

    override suspend fun sendMessage(message: Conversation) {
        repository.sendMessage(message)
    }

    override fun getAllConversation(data: UserChatEntity): Flow<List<Conversation>> {
        return repository.getAllConversation(data)
    }
}