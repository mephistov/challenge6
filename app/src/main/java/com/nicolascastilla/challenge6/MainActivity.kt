package com.nicolascastilla.challenge6

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import android.Manifest
import android.content.Intent
import androidx.activity.viewModels
import com.nicolascastilla.challenge6.activities.StartChatActivity
import com.nicolascastilla.challenge6.viewmodels.NewChatViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModelNewChat: NewChatViewModel by viewModels()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainComposable()
                   /* val viewModel = viewModel<UserDataViewModel>()
                    if(viewModel.isRegisterUser.value){
                        MainComposable()
                    }else{
                        LoginComposable()
                    }*/

                }
            }
        }

        viewModelNewChat.goToChat.observe(this){
            if(it != null && it.containsKey("name")){
                val intent = Intent(baseContext, StartChatActivity::class.java).apply {
                    putExtra("CHATUSER",it.getValue("phone"))
                    putExtra("CHATNAME",it.getValue("name"))
                }
                startActivity(intent)
            }
        }
        viewModel.goToChat.observe(this){
            validateAndGo(it)
        }
    }

    fun validateAndGo(map:HashMap<String,String>?){
        if(map != null && map.containsKey("name")){
            val intent = Intent(baseContext, StartChatActivity::class.java).apply {
                putExtra("CHATUSER",map.getValue("phone"))
                putExtra("CHATNAME",map.getValue("name"))
            }
            startActivity(intent)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChallengeTheme {
        MainComposable()
    }
}