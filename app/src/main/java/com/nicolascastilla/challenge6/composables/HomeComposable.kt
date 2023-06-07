package com.nicolascastilla.challenge6.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.nicolascastilla.challenge6.composables.utils.RemoteImageFull
import com.nicolascastilla.challenge6.viewmodels.MainViewModel
import com.nicolascastilla.domain.repositories.entities.messages.UserChatEntity
import com.nicolascastilla.domain.repositories.extensions.toHumanDate

@Composable
fun HomeComposable(navController: NavHostController?, viewModel: MainViewModel){

    val listItems by viewModel.myItems.collectAsState(initial = emptyList())
    Box(modifier = Modifier.fillMaxSize()){
        if(listItems.size == 0) {
            Text(
                "Here goes the chats when you start to write,\n Loading chats...",
                modifier = Modifier.align(Alignment.Center)
            )
        }else{
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ){
                items(listItems) { item ->
                    RowChats(item) {
                        viewModel.setChatView(it)
                    }
                }
            }
        }
    }

}

@Composable
fun RowChats(item: UserChatEntity, onClickPressed:(UserChatEntity)->Unit){

    if(item.imgProfile == "")
        item.imgProfile = "https://desarrollo.edicionescastillo.com/wp-content/themes/cera-child/assets/images/avatars/user-avatar.png"
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClickPressed(item)
            }
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),

        ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 5.dp)) {

            RemoteImageFull(
                item.imgProfile,
                Modifier
                    .size(60.dp)
                    .align(Alignment.CenterStart)
            )
            Column(
                modifier = Modifier
                    .padding(start = 65.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    item.name,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    item.lastMessage,
                    style = TextStyle(fontSize = 14.sp)
                )
            }
            Text(
                item.timestamp.toHumanDate(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 5.dp, end = 5.dp),
                style = TextStyle(fontSize = 9.sp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewRowConversations() {
    val caht = UserChatEntity(
        name = "el pepe",
        lastMessage = "hola como vas que tal?",
        imgProfile = "https://desarrollo.edicionescastillo.com/wp-content/themes/cera-child/assets/images/avatars/user-avatar.png"
    )
    RowChats(caht, { })
}