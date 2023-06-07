package com.nicolascastilla.challenge6.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.MainActivity
import com.nicolascastilla.challenge6.R
import com.nicolascastilla.challenge6.activities.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge6.ui.theme.SkeyBlue
import com.nicolascastilla.challenge6.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    //private val viewModel by viewModels<UserDataViewModel>()
    private val viewModel: UserDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                            .background(SkeyBlue)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logochat),
                            contentDescription = "Logo",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
        viewModel.isValidated.observe(this){
            if(it){
                val intent = Intent(baseContext, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(baseContext, LoginRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    ChallengeTheme {
        Greeting2("Android")
    }
}