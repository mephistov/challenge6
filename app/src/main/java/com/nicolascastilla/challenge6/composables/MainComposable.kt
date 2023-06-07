package com.nicolascastilla.challenge6.composables

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.viewmodels.MainViewModel
import com.nicolascastilla.challenge6.viewmodels.NewChatViewModel

@Composable
fun MainComposable(){
    val viewModel = viewModel<MainViewModel>()
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModelContacts = viewModel<NewChatViewModel>()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            navController.navigate("routeContacts")
            Log.e("ExampleScreen","PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.e("ExampleScreen","PERMISSION DENIED")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MyNavHost(navController,viewModel,viewModelContacts)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(100.dp)
                .background(BlueGradient)
        ) {
            Row() {
                Button(onClick = {
                    navController.navigate("routeMain")
                }) {
                    Text(text = "Chats")
                }
                Button(onClick = {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_CONTACTS
                        ) -> {
                            // Some works that require permission
                            navController.navigate("routeContacts")
                        }
                        else -> {
                            // Asking for permission
                            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }) {
                   Text(text = "Contactos")
                }
                Button(onClick = {
                    //navController.navigate("routeMain")
                }) {
                    Text(text = "Profile")
                }

            }
        }
        
    }

}

@Composable
fun MyNavHost(
    navController: NavHostController,
    //scaffolState: ScaffoldState,
    viewModel: MainViewModel,
    viewModelNew: NewChatViewModel
) {
    NavHost(navController = navController, startDestination = "routeMain") {
        composable("routeMain") { HomeComposable(navController,viewModel) }
        composable("routeContacts") { NewChatsComposable(navController,viewModelNew) }
    }
}