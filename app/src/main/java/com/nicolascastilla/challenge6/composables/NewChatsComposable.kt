package com.nicolascastilla.challenge6.composables

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.nicolascastilla.challenge6.composables.utils.LoadingScreen
import com.nicolascastilla.challenge6.ui.theme.BlueGradient
import com.nicolascastilla.challenge6.ui.theme.StrokeColor
import com.nicolascastilla.challenge6.ui.theme.TitleColor
import com.nicolascastilla.challenge6.viewmodels.MainViewModel
import com.nicolascastilla.challenge6.viewmodels.NewChatViewModel
import com.nicolascastilla.domain.repositories.entities.contacts.ContactsEntity

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NewChatsComposable(navController: NavHostController, viewModel: NewChatViewModel) {
    var text by remember { mutableStateOf("") }
    var textNumber by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(BlueGradient)
        ) {
            Row() {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = { Text("Search", color = Color.White,style = TextStyle(fontSize = 14.sp)) },
                    shape = RoundedCornerShape(10),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {

                            keyboardController?.hide()
                        }
                    ),
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(0.5f),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.White,

                            )
                    },
                    colors = TextFieldDefaults.colors(
                        contentColorFor(backgroundColor = Color(0x26fcfcff)),
                        unfocusedContainerColor = Color.White,
                        disabledTextColor = Color.White,
                        focusedContainerColor = StrokeColor,
                        cursorColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        color = TitleColor,
                        fontSize = 14.sp
                    )
                )
                Button(onClick = {
                    viewModel.setChats()
                    navController.navigate("routeMain")
                }) {
                    Text(text = "Start Chat")
                }

            }

        }
        if(viewModel.contactsList.value.size == 0){
            LoadingScreen()
        }else{
            LazyColumn {
                items(viewModel.contactsList.value) { contact ->
                    CustomRow(contact)
                }
            }
        }
    }

}


@Composable
fun CustomRow(item: ContactsEntity) {
    var checkedState by remember { mutableStateOf(false) }
    checkedState = item.isChecked
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

            }
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.name)
                Text(text = item.phone)
            }

            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                    item.isChecked = it
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}