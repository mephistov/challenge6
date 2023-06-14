package com.nicolascastilla.challenge6.di
import android.content.Context
import com.google.firebase.FirebaseApp
import com.nicolascastilla.challenge6.utils.SystemSoundImpl
import com.nicolascastilla.data.local.contacts.GetContactsRepository
import com.nicolascastilla.data.local.interfaces_core.SystemSoundUsage
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseSetup {

    @Singleton
    @Provides
    fun provideFirebaseApp(@ApplicationContext context: Context): FirebaseApp {
        val firebaseApp: FirebaseApp? = FirebaseApp.initializeApp(context)

        return firebaseApp ?: throw IllegalStateException("FirebaseApp not initialized")
    }

    @Provides
    fun provideGetSystemSoundRepository(@ApplicationContext context: Context): SystemSoundUsage {
        return SystemSoundImpl(context)
    }

}