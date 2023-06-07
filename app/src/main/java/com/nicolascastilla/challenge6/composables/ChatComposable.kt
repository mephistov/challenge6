package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.viewmodels.ChatViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatComposable(phone: String, name: String, onBackPressed: () -> Unit){

    val messages = remember { mutableStateListOf<String>() }
    var textFieldState by remember { mutableStateOf("") }
    val viewModel = viewModel<ChatViewModel>()
    val coroutineScope = rememberCoroutineScope()
    viewModel.setupFirstChat(name,phone)
    val listState = rememberLazyListState()
   // val chatList by viewModel.chatList.collectAsState(emptyList())

    Column() {
        Box(
            modifier = Modifier
                .statusBarsPadding() // Hace que la caja superior no se mueva
                .fillMaxWidth()
                .background(Color.White)
                //.height(60.dp)
        ) {
            TopAppBar(
                title = { Text(name, textAlign = TextAlign.Center) },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {},
                backgroundColor = BlueGradient,
                contentColor = Color.White,
                elevation = 12.dp,
                modifier = Modifier
            )
        }
        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Green),
            state = listState
        ) {
            itemsIndexed(viewModel.chatList.value) {index, item ->
                Text(item.message)
               /* if (index == viewModel.chatList.value.lastIndex) {
                    LaunchedEffect(key1 = Unit) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(viewModel.chatList.value.lastIndex)
                        }
                    }
                }*/
            }
        }
        Box(
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
                .background(Color.Yellow)
                .height(80.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = textFieldState,
                    onValueChange = { textFieldState = it },
                    Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Escribe un mensaje...") },


                )

                Button(
                    onClick = {
                        viewModel.sendMessage(name,phone,textFieldState)
                        textFieldState = ""
                       /* coroutineScope.launch {
                            listState.animateScrollToItem(viewModel.chatList.value.lastIndex)
                        }*/


                    }
                ) {
                    Text("Enviar")
                }
            }
        }
    }

    if(viewModel.chatList.value.size > 0){
        coroutineScope.launch {
                listState.animateScrollToItem(viewModel.chatList.value.lastIndex)
        }
    }


}
