package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.viewmodels.MainViewModel

@Composable
fun HomeComposable(viewModel: MainViewModel){

    val listItems by viewModel.myItems.collectAsState(initial = emptyList())
Text("aqui viewnen los chats")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        items(listItems) { item ->
            Text("valores")
        }
    }
}