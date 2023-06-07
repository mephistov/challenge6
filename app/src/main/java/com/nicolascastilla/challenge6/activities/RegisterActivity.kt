package com.nicolascastilla.challenge6.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicolascastilla.challenge6.MainActivity
import com.nicolascastilla.challenge6.activities.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge6.composables.RegisterComposable
import com.nicolascastilla.challenge6.viewmodels.RegisterUserDataViewModel
import com.nicolascastilla.challenge6.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val viewModel: RegisterUserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RegisterComposable(onClose = {
                        val intent = Intent(baseContext, LoginRegisterActivity::class.java)
                        startActivity(intent)
                        finish()
                    })
                }
            }
        }
        viewModel.isValidated.observe(this){
            if(it){
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

