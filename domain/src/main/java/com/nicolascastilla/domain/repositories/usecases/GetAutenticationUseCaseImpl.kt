package com.nicolascastilla.domain.repositories.usecases

import com.nicolascastilla.domain.repositories.FirebaseAutenticationRepository
import com.nicolascastilla.domain.repositories.entities.UserData
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAutenticationUseCaseImpl @Inject constructor(
    private val firebaseAut: FirebaseAutenticationRepository
):GetAutenticationUseCase {

    override suspend fun createuser(user: UserData): Flow<UserData?> {
        return firebaseAut.createUser(user)
    }

    override suspend fun isUserAutenticated(user: UserData): Flow<UserData?> {
        return firebaseAut.isUserAutenticated(user)
    }

    override suspend fun isUserLogged(): Flow<UserData?> {
        return firebaseAut.isUserLogged()
    }
}