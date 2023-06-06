package com.nicolascastilla.domain.repositories

import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import kotlinx.coroutines.flow.Flow

interface ChallengeRepository {

    fun getAllData(): Flow<List<UserChatEntity>>
}