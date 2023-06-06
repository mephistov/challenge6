package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicolascastilla.challenge6.activities.ui.theme.ChallengeTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicolascastilla.challenge6.ui.theme.PrimaryBlue
import com.nicolascastilla.challenge6.ui.theme.TitleColor
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.R
import com.nicolascastilla.challenge6.composables.utils.LoadingScreen
import com.nicolascastilla.challenge6.ui.theme.BackgroundColor
import com.nicolascastilla.challenge6.viewmodels.UserDataViewModel

@Composable
fun LoginComposable(){
    //var email by remember { mutableStateOf("") }
   // var password by remember { mutableStateOf("") }
    val viewModel = viewModel<UserDataViewModel>()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            )
    {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                //.verticalScroll(rememberScrollState())
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(R.drawable.logochat), contentDescription = "Logo")
            Text(
                text = "Sign In / Register to Nick Chat",
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = {viewModel.email.value = it },
                placeholder = { Text("Email", color = TitleColor,style = TextStyle(fontSize = 14.sp)) },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = PrimaryBlue
                )
            )
            OutlinedTextField(
                value = viewModel.phone.value,
                onValueChange = {viewModel.phone.value = it },
                placeholder = { Text("Phone", color = TitleColor,style = TextStyle(fontSize = 14.sp)) },
                label = { Text("Phone") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = PrimaryBlue
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = {viewModel.password.value = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = PrimaryBlue
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.validateUser() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go Inside")
            }
        }
        if(viewModel.isLoading.value)
            LoadingScreen()
    }
}

