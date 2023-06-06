package com.nicolascastilla.challenge6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.composables.LoginComposable
import com.nicolascastilla.challenge6.composables.MainComposable
import com.nicolascastilla.challenge6.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge6.viewmodels.MainViewModel
import com.nicolascastilla.challenge6.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<UserDataViewModel>()
                    if(viewModel.isRegisterUser.value){
                        MainComposable()
                    }else{
                        LoginComposable()
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChallengeTheme {
        LoginComposable()
    }
}