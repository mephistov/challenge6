package com.nicolascastilla.challenge6.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.nicolascastilla.challenge6.activities.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge6.composables.ChatComposable
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartChatActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toChatDAta = intent?.getStringExtra("CHATUSER")
        val toChatName = intent?.getStringExtra("CHATNAME")
        Log.e("TEST","usertoSearch: ${toChatDAta}")

        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BlueGradient
                ) {
                    ChatComposable(toChatDAta!!,toChatName!!) {
                        finish()
                    }
                }
            }
        }
    }
}