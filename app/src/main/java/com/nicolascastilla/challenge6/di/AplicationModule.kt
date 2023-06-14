package com.nicolascastilla.challenge6.di

import android.content.Context
import com.nicolascastilla.challenge6.utils.SystemSoundImpl
import com.nicolascastilla.data.local.contacts.GetContactsRepository
import com.nicolascastilla.data.local.interfaces_core.SystemSoundUsage
import com.nicolascastilla.domain.repositories.usecases.GetAutenticationUseCaseImpl
import com.nicolascastilla.domain.repositories.usecases.GetChatUseCasesImpl
import com.nicolascastilla.domain.repositories.usecases.GetInfoUseCaseImpl
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetChatUseCases
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AplicationModule {

    @Binds
    abstract fun bindsGetInfoUseCases(useCaseImpl: GetInfoUseCaseImpl ): GetInfoUseCase

    @Binds
    abstract fun bindsGetDataUseCases(useCaseImpl: GetAutenticationUseCaseImpl): GetAutenticationUseCase
    @Binds
    abstract fun bindsGetChatUseCases(useCaseImpl: GetChatUseCasesImpl): GetChatUseCases



}