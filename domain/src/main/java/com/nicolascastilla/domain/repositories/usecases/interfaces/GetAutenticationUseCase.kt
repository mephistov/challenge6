package com.nicolascastilla.domain.repositories.usecases.interfaces

import com.nicolascastilla.domain.repositories.entities.UserData
import kotlinx.coroutines.flow.Flow

interface GetAutenticationUseCase {
    suspend fun createuser(user:UserData): Flow<UserData?>
    suspend fun isUserAutenticated(user: UserData):Flow<UserData?>
    suspend fun isUserLogged():Flow<UserData?>
}