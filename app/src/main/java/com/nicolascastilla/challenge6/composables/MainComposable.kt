package com.nicolascastilla.challenge6.composables

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.ui.theme.StrokeColor
import com.nicolascastilla.challenge6.viewmodels.MainViewModel
import com.nicolascastilla.challenge6.viewmodels.NewChatViewModel

@Composable
fun MainComposable(){
    val viewModel = viewModel<MainViewModel>()
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModelContacts = viewModel<NewChatViewModel>()



    Box(modifier = Modifier.fillMaxSize().background(StrokeColor)) {
        MyNavHost(navController,viewModel,viewModelContacts)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()

        ) {
            BannerBottom(navController,viewModel)
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

@Composable
fun BannerBottom(navController: NavHostController,viewModel: MainViewModel) {
    val context = LocalContext.current


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
    Box(
        modifier = Modifier.fillMaxWidth()
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 10.dp)
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                //.background(Color.Yellow)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .background(Color.White)
                .align(Alignment.BottomCenter)
        )
        IconButton(
            onClick = {
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
                viewModel.selectedChatColor.value = StrokeColor
                viewModel.selectedProfileColor.value = StrokeColor
            },
            modifier = Modifier.background(
                color = BlueGradient,
                shape = CircleShape
            )
                .align(Alignment.TopCenter)
                .size(60.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                tint = Color.White,
                //modifier = Modifier.size(40.dp)
            )
        }
        Box(

            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 15.dp, bottom = 5.dp)
                .clickable {
                    navController.navigate("routeMain")
                    viewModel.selectedChatColor.value = BlueGradient
                    viewModel.selectedProfileColor.value = StrokeColor
                }
                .background(Color.Transparent),

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = com.nicolascastilla.challenge6.R.drawable.speech_bubble),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(viewModel.selectedChatColor.value)
                )
                Text(text = "Conversationes", style = TextStyle(fontSize = 10.sp, color = viewModel.selectedChatColor.value))
            }
        }
        Box(

            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 15.dp, bottom = 5.dp)
                .clickable {
                    viewModel.selectedChatColor.value = StrokeColor
                    viewModel.selectedProfileColor.value = BlueGradient

                }
                .background(Color.Transparent),

            ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = com.nicolascastilla.challenge6.R.drawable.avatar),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(viewModel.selectedProfileColor.value)
                )
                Text(text = "Profile", style = TextStyle(fontSize = 10.sp, color = viewModel.selectedProfileColor.value))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun Defaultbanner() {
    //BannerBottom(navController)
}