package com.nicolascastilla.challenge6.composables.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.nicolascastilla.challenge6.ui.theme.BlueGradient

@Composable
fun LoadingScreen(){

    Box(
        modifier = Modifier.fillMaxSize()
            .background(BlueGradient)
            .zIndex(1f)
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }

}