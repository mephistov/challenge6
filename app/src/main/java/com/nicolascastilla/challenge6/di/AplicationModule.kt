package com.nicolascastilla.challenge6.di

import com.nicolascastilla.domain.repositories.usecases.GetAutenticationUseCaseImpl
import com.nicolascastilla.domain.repositories.usecases.GetInfoUseCaseImpl
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetAutenticationUseCase
import com.nicolascastilla.domain.repositories.usecases.interfaces.GetInfoUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AplicationModule {

    @Binds
    abstract fun bindsGetInfoUseCases(useCaseImpl: GetInfoUseCaseImpl): GetInfoUseCase

    @Binds
    abstract fun bindsGetDataUseCases(useCaseImpl: GetAutenticationUseCaseImpl): GetAutenticationUseCase


}