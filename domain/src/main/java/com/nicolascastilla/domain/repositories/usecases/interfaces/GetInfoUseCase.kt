package com.nicolascastilla.domain.repositories.usecases.interfaces

import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import kotlinx.coroutines.flow.Flow

interface GetInfoUseCase {
    fun getAllUserInfo(): Flow<List<UserChatEntity>>
}