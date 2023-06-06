package com.nicolascastilla.domain.repositories

import com.nicolascastilla.domain.repositories.entities.UserData
import kotlinx.coroutines.flow.Flow

interface FirebaseAutenticationRepository {
    suspend fun isUserAutenticated(user: UserData): Flow<UserData?>
    suspend fun createUser(user:UserData):Flow<UserData?>
    suspend fun isUserLogged():Flow<UserData?>
}