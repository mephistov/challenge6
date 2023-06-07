package com.nicolascastilla.data.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.Preferences
import com.nicolascastilla.data.local.preferences.UserPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.nicolascastilla.data.local.contacts.GetContactsRepository
import com.nicolascastilla.data.network.api.ChallengeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    var logging: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    var httpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logging)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://someurl")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideChallengeApi(retrofit: Retrofit): ChallengeApi {
        return retrofit.create(ChallengeApi::class.java)
    }

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideGetContactsRepository(@ApplicationContext context: Context):GetContactsRepository{
        return GetContactsRepository(context)
    }

}