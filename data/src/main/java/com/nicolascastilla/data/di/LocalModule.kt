package com.nicolascastilla.data.di

import com.nicolascastilla.data.RepositoryImp
import com.nicolascastilla.data.firebase.FirebaseAutentication
import com.nicolascastilla.domain.repositories.ChallengeRepository
import com.nicolascastilla.domain.repositories.FirebaseAutenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {

    @Binds
    abstract fun bindsRepository(repoImpl: RepositoryImp): ChallengeRepository

    @Binds
    abstract fun bindsAuthoritationRepository(repoImpl: FirebaseAutentication): FirebaseAutenticationRepository
}