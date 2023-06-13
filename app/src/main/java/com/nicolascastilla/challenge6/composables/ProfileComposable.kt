package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nicolascastilla.challenge6.R
import com.nicolascastilla.challenge6.viewmodels.MainViewModel

@Composable
fun ProfileComposable(navController: NavHostController, viewModel: MainViewModel) {
    viewModel.gerUserInfo()
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(text = viewModel.nameProfile.value, )
        Text(text = viewModel.phoneProfile.value, )
        Text(text = viewModel.emailProfile.value, )
    }
}