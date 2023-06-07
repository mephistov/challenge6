package com.nicolascastilla.domain.repositories.usecases

import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.repositories.entities.MessageItem
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInfoUseCaseImpl @Inject constructor(
    private val repository: ChallengeRepository
): GetInfoUseCase {

    override fun getAllUserInfo(): Flow<List<UserChatEntity>> {
        return repository.getAllData()
    }

    override fun getAllContacts(): Flow<List<ContactsEntity>> {
        return repository.getAllContacts()
    }
}