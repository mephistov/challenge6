package com.nicolascastilla.challenge6

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.google.firebase.FirebaseApp
@HiltAndroidApp
class ChallengeApplication:Application() {

 /*   override fun onCreate() {
        super.onCreate()
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }
    }*/
}