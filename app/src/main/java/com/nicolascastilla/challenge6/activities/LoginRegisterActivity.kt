package com.nicolascastilla.challenge6.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import com.nicolascastilla.challenge6.composables.LoginComposable
import com.nicolascastilla.challenge6.viewmodels.UserDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.migration.CustomInjection.inject

@AndroidEntryPoint
class LoginRegisterActivity : ComponentActivity() {

    private val viewModel: UserDataViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setContent {
            ChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                   LoginComposable(onClose = {
                       val intent = Intent(baseContext, RegisterActivity::class.java)
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

    fun requestPermission(){
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //Toast.makeText(baseContext,"Thanks, you can continue using the app :)",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(baseContext,"Sorry, you cant see the contacts :(", Toast.LENGTH_LONG).show()
            }
        }
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        // }
    }
}

