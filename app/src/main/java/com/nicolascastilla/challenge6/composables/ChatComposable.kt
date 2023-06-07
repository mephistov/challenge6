package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.ui.theme.ChallengeTheme
import com.nicolascastilla.challenge6.ui.theme.PrimaryBlue
import com.nicolascastilla.challenge6.ui.theme.StrokeColor
import com.nicolascastilla.challenge6.viewmodels.ChatViewModel
import com.nicolascastilla.domain.repositories.entities.messages.Conversation
import com.nicolascastilla.domain.repositories.extensions.toHumanDate
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
                .fillMaxWidth(),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(viewModel.chatList.value) {index, item ->
                if(phone == item.idUser) {
                    ChatBubbleMy(item.message,item.timestamp.toHumanDate())
                }else{
                    ChatBubbleOther(item.message,item.timestamp.toHumanDate())
                }
               //Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Box(
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
                .background(Color.White)
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
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = PrimaryBlue, // color de fondo del botón
                        contentColor = Color.White // color del contenido del botón (ej. texto)
                    ),
                    onClick = {
                        if(textFieldState != "") {
                            viewModel.sendMessage(name, phone, textFieldState)
                            textFieldState = ""
                        }

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

@Composable
fun ChatBubbleMy( message:String,time:String) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterEnd)
                //.shadow(4.dp)
        ) {
            val bubbleModifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(16.dp)

            Text(
                message,
                modifier = bubbleModifier
            )
            Text(
                time,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(end = 5.dp),
                style = TextStyle(fontSize = 8.sp)

            )
            Canvas(modifier = Modifier
                .size(10.dp)
                .align(Alignment.BottomEnd)
            ) {
                drawPath(
                    path = Path().apply {
                        moveTo(size.width+20, size.height)
                        lineTo(size.width, 0f)
                        lineTo(0f, size.height)
                        close()
                    },
                    color = Color.White
                )
            }
        }
    }
}
//fun ChatBubbleOther(modifier: Modifier = Modifier, content: @Composable (Modifier) -> Unit, time:String) {}
@Composable
fun ChatBubbleOther( message:String,time:String) {

    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
                //.shadow(4.dp)
        ) {
            val bubbleModifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(16.dp)

            Text(
                message,
                modifier = bubbleModifier
            )
            Text(
                time,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(end = 5.dp),
                style = TextStyle(fontSize = 8.sp)

            )

            Canvas(modifier = Modifier
                .size(10.dp)
                .align(Alignment.BottomStart)
            ) {
                drawPath(
                    path = Path().apply {
                        moveTo(-20F, size.height)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        close()
                    },
                    color = Color.White
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val conv = Conversation(
        idUser = "12345678",
        image = "",
        message = "hola mundo como estas",
        name = "pepe guapo",
        timestamp = 1686096667155

    )
    ChallengeTheme {
        Column(modifier = Modifier.background(StrokeColor)) {
            ChatBubbleMy(conv.message,conv.timestamp.toHumanDate())
            ChatBubbleOther(conv.message,conv.timestamp.toHumanDate())
        }

    }
}
